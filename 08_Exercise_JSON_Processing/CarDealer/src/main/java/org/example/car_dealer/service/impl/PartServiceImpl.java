package org.example.car_dealer.service.impl;

import com.google.gson.Gson;
import org.example.car_dealer.data.entities.Part;
import org.example.car_dealer.data.entities.Supplier;
import org.example.car_dealer.data.repositories.PartRepository;
import org.example.car_dealer.data.repositories.SupplierRepository;
import org.example.car_dealer.service.PartService;
import org.example.car_dealer.service.dtos.importDtos.part.PartDto;
import org.example.car_dealer.utli.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String FILE_PATH = "src/main/resources/json_import_data/parts.json";

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final Gson gson;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, ValidatorComponent validatorComponent, Gson gson) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
        this.gson = gson;
    }

    @Override
    public void seedParts() throws IOException {
        if (this.partRepository.count() != 0) {
            return;
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            PartDto[] partDtos = this.gson.fromJson(reader, PartDto[].class);

            List<Supplier> suppliers = this.supplierRepository.findAll();
            for (PartDto partDto : partDtos) {
                if (!this.validatorComponent.isValid(partDto)) {
                    this.validatorComponent.validate(partDto).forEach(error -> System.out.println(error.getMessage()));
                    continue;
                }

                Part part = this.modelMapper.map(partDto, Part.class);
                part.setSupplier(suppliers.get(ThreadLocalRandom.current().nextInt(suppliers.size())));
                this.partRepository.saveAndFlush(part);
            }
        }
    }
}
