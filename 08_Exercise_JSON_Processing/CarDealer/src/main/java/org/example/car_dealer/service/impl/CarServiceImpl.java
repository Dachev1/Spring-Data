package org.example.car_dealer.service.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.example.car_dealer.data.entities.Car;
import org.example.car_dealer.data.entities.Part;
import org.example.car_dealer.data.repositories.CarRepository;
import org.example.car_dealer.data.repositories.PartRepository;
import org.example.car_dealer.service.CarService;
import org.example.car_dealer.service.dtos.exportDtos.car.CarInfoDto;
import org.example.car_dealer.service.dtos.exportDtos.car.CarWithPartsDto;
import org.example.car_dealer.service.dtos.exportDtos.part.PartForCarsDto;
import org.example.car_dealer.service.dtos.importDtos.car.CarDto;
import org.example.car_dealer.utli.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CarServiceImpl implements CarService {
    private static final String FILE_PATH = "src/main/resources/json_import_data/cars.json";
    private static final int MIN_PARTS = 3;
    private static final int MAX_PARTS = 5;

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, ValidatorComponent validatorComponent, Gson gson) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void seedCars() throws IOException {
        if (this.carRepository.count() != 0) {
            return;
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            CarDto[] carDtos = this.gson.fromJson(reader, CarDto[].class);

            for (CarDto carDto : carDtos) {
                if (!this.validatorComponent.isValid(carDto)) {
                    this.validatorComponent.validate(carDto).forEach(error -> System.out.println(error.getMessage()));
                    continue;
                }

                Car car = this.modelMapper.map(carDto, Car.class);

                // Fetch random parts and ensure they're managed by Hibernate
                List<Part> randomParts = getRandomParts(MIN_PARTS + ThreadLocalRandom.current().nextInt(MAX_PARTS - MIN_PARTS + 1));
                car.setParts(randomParts);

                this.carRepository.saveAndFlush(car);
            }
        }
    }

    @Override
    public String getALlCarsByMake(String make) {
        List<Car> carList = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make);

        List<CarInfoDto> carInfoDtos = carList.stream()
                .map(car -> this.modelMapper.map(car, CarInfoDto.class))
                .toList();

        return this.gson.toJson(carInfoDtos);
    }

    private List<Part> getRandomParts(int partCount) {
        List<Part> allParts = this.partRepository.findAll();
        List<Part> selectedParts = new ArrayList<>();

        for (int i = 0; i < partCount; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(allParts.size());
            Part randomPart = allParts.get(randomIndex); // Use get instead of remove to avoid detached entity issues
            selectedParts.add(randomPart);
        }

        return selectedParts;
    }

    @Override
    public String getCarsWithTheirPartsAsJson() {
        List<Car> cars = this.carRepository.findAll();

        List<CarWithPartsDto> carWithPartsDtos = cars.stream().map(car -> {
            CarWithPartsDto carDto = this.modelMapper.map(car, CarWithPartsDto.class);

            // Manually map parts to PartDto
            List<PartForCarsDto> partDtos = car.getParts().stream()
                    .map(part -> this.modelMapper.map(part, PartForCarsDto.class))
                    .toList();

            carDto.setParts(partDtos);
            return carDto;
        }).toList();

        return gson.toJson(carWithPartsDtos);
    }
}
