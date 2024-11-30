package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.xml.visitor.VisitorRootDTO;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.VisitorService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VisitorServiceImpl implements VisitorService {
    private static final String FILE_VISITORS = "src/main/resources/files/xml/visitors.xml";

    private final VisitorRepository visitorRepository;
    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;
    private final PersonalDataRepository personalDataRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public VisitorServiceImpl(VisitorRepository visitorRepository, AttractionRepository attractionRepository, CountryRepository countryRepository, PersonalDataRepository personalDataRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.visitorRepository = visitorRepository;
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.personalDataRepository = personalDataRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.visitorRepository.count() > 0;
    }

    @Override
    public String readVisitorsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_VISITORS));
    }

    @Override
    public String importVisitors() throws JAXBException {
        StringBuilder result = new StringBuilder();

        VisitorRootDTO visitorRootDTO = this.xmlParser.fromFile(FILE_VISITORS, VisitorRootDTO.class);

        visitorRootDTO.getVisitors().forEach(dto -> {
            boolean isDuplicateName = this.visitorRepository
                    .findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())
                    .isPresent();

            boolean isDuplicatePersonalData = this.visitorRepository
                    .findByPersonalDataId(dto.getPersonalDataId())
                    .isPresent();

            if (this.validationUtil.isValid(dto) && !isDuplicateName && !isDuplicatePersonalData) {
                Visitor visitor = this.modelMapper.map(dto, Visitor.class);

                visitor.setAttraction(this.attractionRepository.findById(dto.getAttractionId()).orElseThrow());
                visitor.setCountry(this.countryRepository.findById(dto.getCountryId()).orElseThrow());
                visitor.setPersonalData(this.personalDataRepository.findById(dto.getPersonalDataId()).orElseThrow());

                this.visitorRepository.saveAndFlush(visitor);

                result.append(String.format("Successfully imported visitor %s %s%n",
                        visitor.getFirstName(), visitor.getLastName()));
            } else {
                result.append("Invalid visitor").append(System.lineSeparator());
            }
        });

        return result.toString().trim();
    }

}
