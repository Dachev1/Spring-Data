package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.VolcanologistSeedDTO;
import softuni.exam.models.dto.imports.VolcanologistSeedRootDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {
    private static final String FILE_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoService volcanoService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, VolcanoService volcanoService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoService = volcanoService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        VolcanologistSeedRootDTO rootDTO = this.xmlParser.fromFile(FILE_PATH, VolcanologistSeedRootDTO.class);

        for (VolcanologistSeedDTO dto : rootDTO.getVolcanologists()) {
            if (!this.validationUtil.isValid(dto) ||
                    this.volcanologistRepository.existsByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())) {
                result.append("Invalid volcanologist").append(System.lineSeparator());
                continue;
            }

            Volcano volcano = this.volcanoService.findVolcanoById(dto.getExploringVolcanoId());
            if (volcano == null) {
                result.append("Invalid volcanologist").append(System.lineSeparator());
                continue;
            }

            Volcanologist volcanologist = modelMapper.map(dto, Volcanologist.class);
            volcanologist.setVolcano(volcano);

            this.volcanologistRepository.saveAndFlush(volcanologist);
            result.append(String.format("Successfully imported volcanologist %s %s",
                            dto.getFirstName(), dto.getLastName()))
                    .append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
