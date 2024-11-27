package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.xml.apartment.ApartmentRootDTO;
import softuni.exam.models.dto.imports.xml.apartment.ApartmentSeedDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.validation.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private static final String FILE_PATH = "src/main/resources/files/xml/apartments.xml";

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        ApartmentRootDTO apartmentRootDTO = this.xmlParser.fromFile(FILE_PATH, ApartmentRootDTO.class);

        for (ApartmentSeedDTO apartmentDTO : apartmentRootDTO.getApartments()) {
            boolean isDuplicate = this.apartmentRepository
                    .findByTownTownNameAndArea(apartmentDTO.getTown(), apartmentDTO.getArea())
                    .isPresent();

            if (!this.validationUtil.isValid(apartmentDTO) || isDuplicate) {
                result.append(String.format("Invalid apartment%n"));
                continue;
            }

            Apartment apartment = new Apartment();
            apartment.setApartmentType(apartmentDTO.getApartmentType());
            apartment.setArea(apartmentDTO.getArea());

            Town town = (Town) this.townRepository.findByTownName(apartmentDTO.getTown()).get();
            apartment.setTown(town);

            this.apartmentRepository.save(apartment);

            result.append(String.format("Successfully imported apartment %s - %.2f%n",
                    apartment.getApartmentType(), apartment.getArea()));
        }

        return result.toString();
    }
}
