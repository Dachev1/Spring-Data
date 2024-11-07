package org.example.nlt.service.impl;

import jakarta.xml.bind.JAXBException;
import org.example.nlt.model.dto.company.CompaniesWrapper;
import org.example.nlt.model.entity.Company;
import org.example.nlt.repository.CompanyRepository;
import org.example.nlt.service.CompanyService;
import org.example.nlt.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private static final String XML_PATH = "src/main/resources/files/xmls/companies.xml";

    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public String getCompaniesXmlData() {
        try {
            return Files.readString(Path.of(XML_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error reading companies XML file", e);
        }
    }

    @Override
    @Transactional
    public void importCompanies() throws JAXBException {
        if (!areImported()) {
            CompaniesWrapper wrapper = xmlParser.fromXml(getCompaniesXmlData(), CompaniesWrapper.class);
            List<Company> companies = wrapper.getCompanies().stream()
                    .map(dto -> modelMapper.map(dto, Company.class))
                    .filter(company -> !companyRepository.existsByName(company.getName()))
                    .toList();
            companyRepository.saveAll(companies);
        }
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }
}
