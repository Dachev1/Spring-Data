package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.xml.personalData.PersonalDataRootDTO;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private static final String FILE_PERSONAL_DATA = "src/main/resources/files/xml/personal_data.xml";

    private final PersonalDataRepository personalDataRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.personalDataRepository = personalDataRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PERSONAL_DATA));
    }

    @Override
    public String importPersonalData() throws JAXBException {
        StringBuilder result = new StringBuilder();

        PersonalDataRootDTO personalDataRootDTO = this.xmlParser.fromFile(FILE_PERSONAL_DATA, PersonalDataRootDTO.class);

        personalDataRootDTO.getPersonalDataList().forEach(personalDataDTO -> {
            if (this.validationUtil.isValid(personalDataDTO)
                    && this.personalDataRepository.findByCardNumber(personalDataDTO.getCardNumber()).isEmpty()) {
                PersonalData personalData = modelMapper.map(personalDataDTO, PersonalData.class);
                this.personalDataRepository.saveAndFlush(personalData);
                result.append(String.format(
                        "Successfully imported personal data for visitor with card number %s%n",
                        personalData.getCardNumber()
                ));
            } else {
                result.append("Invalid personal data").append(System.lineSeparator());
            }
        });

        return result.toString().trim();
    }

}
