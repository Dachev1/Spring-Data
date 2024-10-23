package org.example.car_dealer.service.impl;

import com.google.gson.Gson;
import org.example.car_dealer.data.entities.Supplier;
import org.example.car_dealer.data.repositories.SupplierRepository;
import org.example.car_dealer.service.SupplierService;
import org.example.car_dealer.service.dtos.exportDtos.supplier.SupplierDto;
import org.example.car_dealer.utli.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String FILE_PATH = "src/main/resources/json_import_data/suppliers.json";

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final Gson gson;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidatorComponent validatorComponent, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (this.supplierRepository.count() != 0) {
            return;
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            org.example.car_dealer.service.dtos.importDtos.supplier.SupplierDto[] supplierDtos = this.gson.fromJson(reader, org.example.car_dealer.service.dtos.importDtos.supplier.SupplierDto[].class);

            for (org.example.car_dealer.service.dtos.importDtos.supplier.SupplierDto supplierDto : supplierDtos) {
                if (!this.validatorComponent.isValid(supplierDto)) {
                    this.validatorComponent.validate(supplierDto).forEach(error -> System.out.println(error.getMessage()));
                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }


    @Override
    public String getLocalSuppliersAsJson() {
        List<Supplier> suppliers = supplierRepository.findAll();

        List<SupplierDto> localSuppliers = suppliers.stream()
                .filter(supplier -> !supplier.isImporter())
                .map(supplier -> {
                    SupplierDto dto = modelMapper.map(supplier, SupplierDto.class);
                    dto.setPartsCount((long) supplier.getParts().size());
                    return dto;
                }).toList();

        return this.gson.toJson(localSuppliers);
    }
}
