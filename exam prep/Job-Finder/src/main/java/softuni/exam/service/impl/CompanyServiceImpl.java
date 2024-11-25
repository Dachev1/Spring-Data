package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.xml.CompanyDTO;
import softuni.exam.models.dto.imports.xml.CompanyRootDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String FILE_PATH = "src/main/resources/files/xml/companies.xml";

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        CompanyRootDTO rootDTO = this.xmlParser.fromFile(FILE_PATH, CompanyRootDTO.class);

        return rootDTO.getCompanies().stream()
                .map(this::importCompany)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCompany(CompanyDTO dto) {
        if (!this.validationUtil.isValid(dto) ||
                this.companyRepository.existsByName(dto.getName())) {
            return "Invalid company";
        }



        Company company = this.modelMapper.map(dto, Company.class);

        Country country = this.countryRepository.findById(dto.getCountryId())
                .orElse(null);
        company.setCountry(country);

        this.companyRepository.saveAndFlush(company);

        return String.format("Successfully imported company %s - %d", company.getName(), country.getId());
    }
}
