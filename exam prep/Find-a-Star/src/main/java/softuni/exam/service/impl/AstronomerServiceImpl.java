package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.AstronomerSeedDTO;
import softuni.exam.models.dto.imports.AstronomersSeedRootDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private final static String FILE_PATH = "src/main/resources/files/xml/astronomers.xml";

    private final AstronomerRepository astronomerRepository;
    private final StarRepository starRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(AstronomersSeedRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AstronomersSeedRootDTO rootDTO = (AstronomersSeedRootDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (AstronomerSeedDTO astronomerDTO : rootDTO.getAstronomers()) {
            Optional<Astronomer> existingAstronomer = this.astronomerRepository.findByFirstNameAndLastName(
                    astronomerDTO.getFirstName(), astronomerDTO.getLastName());
            Optional<Star> observingStar = this.starRepository.findById(astronomerDTO.getObservingStarId());

            if (!this.validationUtil.isValid(astronomerDTO) || existingAstronomer.isPresent() || observingStar.isEmpty()) {
                sb.append("Invalid astronomer").append(System.lineSeparator());
                continue;
            }

            Astronomer astronomer = this.modelMapper.map(astronomerDTO, Astronomer.class);
            astronomer.setObservingStar(observingStar.get());

            this.astronomerRepository.saveAndFlush(astronomer);

            sb.append(String.format("Successfully imported astronomer %s %s - %.2f",
                            astronomer.getFirstName(), astronomer.getLastName(), astronomer.getAverageObservationHours()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
