package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.exports.BorrowingRecordExportDTO;
import softuni.exam.models.dto.imports.xml.BorrowingRecordSeedDTO;
import softuni.exam.models.dto.imports.xml.BorrowingRecordsRootDTO;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository recordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository recordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.recordRepository = recordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.recordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        // Parse XML to DTO
        BorrowingRecordsRootDTO rootDTO = xmlParser
                .fromFile(FILE_PATH, BorrowingRecordsRootDTO.class);

        for (BorrowingRecordSeedDTO recordDTO : rootDTO.getRecords()) {
            if (!validationUtil.isValid(recordDTO)
                    || !bookRepository.existsByTitle(recordDTO.getBook().getTitle())
                    || !libraryMemberRepository.existsById(recordDTO.getMember().getId())) {
                result.append("Invalid borrowing record").append(System.lineSeparator());
                continue;
            }

            // Map and Save Entity
            BorrowingRecord record = modelMapper.map(recordDTO, BorrowingRecord.class);
            record.setBook(bookRepository.findByTitle(recordDTO.getBook().getTitle()).orElse(null));
            record.setMember(libraryMemberRepository.findById(recordDTO.getMember().getId()).orElse(null));

            recordRepository.save(record);

            result.append(String.format("Successfully imported borrowing record %s - %s",
                            record.getBook().getTitle(), record.getBorrowDate()))
                    .append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    @Override
    public String exportBorrowingRecords() {
        List<BorrowingRecordExportDTO> records = recordRepository.findBorrowingRecordsBeforeDate(LocalDate.of(2021, 9, 10));

        StringBuilder result = new StringBuilder();
        for (BorrowingRecordExportDTO dto : records) {
            result.append(dto.toString()).append("\n");
        }

        return result.toString().trim();
    }
}
