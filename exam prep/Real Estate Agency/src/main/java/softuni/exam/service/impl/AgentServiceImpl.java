package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.AgentSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AgentServiceImpl implements AgentService {
    private static final String FILE_PATH = "src/main/resources/files/json/agents.json";

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder result = new StringBuilder();

        AgentSeedDTO[] agents = this.gson.fromJson(readAgentsFromFile(), AgentSeedDTO[].class);

        for (AgentSeedDTO agentDTO : agents) {
            boolean isFirstNameDuplicate = this.agentRepository.findByFirstName(agentDTO.getFirstName()).isPresent();
            boolean isTownValid = this.townRepository.findByTownName(agentDTO.getTown()).isPresent();

            if (!this.validationUtil.isValid(agentDTO) || isFirstNameDuplicate || !isTownValid) {
                result.append(String.format("Invalid agent%n"));
                continue;
            }

            Town town = (Town) this.townRepository.findByTownName(agentDTO.getTown()).get();
            Agent agent = this.modelMapper.map(agentDTO, Agent.class);
            agent.setTown(town);
            this.agentRepository.save(agent);

            result.append(String.format("Successfully imported agent %s %s%n",
                    agent.getFirstName(), agent.getLastName()));
        }

        return result.toString();
    }
}
