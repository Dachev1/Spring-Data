package com.example.football.service.impl;

import com.example.football.models.dto.imports.xml.stat.StatRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.validation.ValidationUtil;
import com.example.football.util.xmlParser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {
    private static final String FILE_PATH = "C:\\Users\\pffe3\\Desktop\\Player-Finder\\skeleton\\src\\main\\resources\\files\\xml\\stats.xml";

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException {
        StringBuilder result = new StringBuilder();

        StatRootDTO statRootDTO = this.xmlParser.fromFile(FILE_PATH, StatRootDTO.class);

        statRootDTO.getStats().forEach(statDTO -> {
            if (!this.validationUtil.isValid(statDTO)) {
                result.append("Invalid Stat").append(System.lineSeparator());
                return;
            }

            Stat stat = this.modelMapper.map(statDTO, Stat.class);
            this.statRepository.saveAndFlush(stat);

            result.append(String.format(
                    "Successfully imported Stat %.2f - %.2f - %.2f",
                    stat.getPassing(),
                    stat.getShooting(),
                    stat.getEndurance()
            )).append(System.lineSeparator());
        });

        return result.toString().trim();
    }
}
