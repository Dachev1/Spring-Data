package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.LibraryMemberSeedDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private static final String FILE_PATH = "src/main/resources/files/json/library-members.json";

    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder result = new StringBuilder();

        LibraryMemberSeedDTO[] libraryMemberSeedDTOS = gson.fromJson(
                readLibraryMembersFileContent(), LibraryMemberSeedDTO[].class);

        for (LibraryMemberSeedDTO memberDTO : libraryMemberSeedDTOS) {
            if (!validationUtil.isValid(memberDTO) || libraryMemberRepository.existsByPhoneNumber(memberDTO.getPhoneNumber())) {
                result.append("Invalid library member").append(System.lineSeparator());
                continue;
            }

            LibraryMember libraryMember = modelMapper.map(memberDTO, LibraryMember.class);

            libraryMemberRepository.save(libraryMember);

            result.append(String.format("Successfully imported library member %s - %s",
                            libraryMember.getFirstName(), libraryMember.getLastName()))
                    .append(System.lineSeparator());
        }

        return result.toString().trim();
    }

}
