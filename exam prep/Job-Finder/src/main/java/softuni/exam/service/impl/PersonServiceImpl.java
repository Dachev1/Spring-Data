package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.PersonSeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private static final String FILE_PATH = "src/main/resources/files/json/people.json";

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PersonServiceImpl(PersonRepository personRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        String fileContent = readPeopleFromFile();

        PersonSeedDTO[] personSeedDTOs = this.gson.fromJson(fileContent, PersonSeedDTO[].class);

        return Arrays.stream(personSeedDTOs)
                .map(this::importPerson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPerson(PersonSeedDTO dto) {
        if (!this.validationUtil.isValid(dto) ||
                this.personRepository.existsByFirstNameOrEmailOrPhone(dto.getFirstName(), dto.getEmail(), dto.getPhone())) {
            return "Invalid person";
        }

        Person person = this.modelMapper.map(dto, Person.class);

        Country country = this.countryRepository.findById(dto.getCountry()).orElse(null);
        if (country == null) {
            return "Invalid person";
        }
        person.setCountry(country);

        this.personRepository.saveAndFlush(person);
        return String.format("Successfully imported person %s %s", person.getFirstName(), person.getLastName());
    }
}