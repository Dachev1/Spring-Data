package org.example.product_shop.service.impl;

import com.google.gson.Gson;
import org.example.product_shop.data.entities.User;
import org.example.product_shop.data.repositories.UserRepository;
import org.example.product_shop.service.UserService;
import org.example.product_shop.service.dtos.exportDtos.product.ProductDto;
import org.example.product_shop.service.dtos.exportDtos.user.UserSoldProductsInfoDto;
import org.example.product_shop.service.dtos.exportDtos.user.UserWithSoldProductsDto;
import org.example.product_shop.service.dtos.exportDtos.user.UsersWithProductsResultDto;
import org.example.product_shop.service.dtos.importDtos.user.UserSeedDto;
import org.example.product_shop.util.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final static String FILE_PATH = "src/main/resources/01_json_input_data/users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorComponent validator;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Gson gson, ValidatorComponent validator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public void seedUserData() throws FileNotFoundException {
        if (this.userRepository.count() != 0) {
            return;
        }

        UserSeedDto[] userSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), UserSeedDto[].class);

        for (UserSeedDto userDto : userSeedDtos) {
            if (!this.validator.isValid(userDto)) {
                this.validator.validate(userDto)
                        .forEach(error -> System.out.println(error.getMessage()));
                continue;
            }

            this.userRepository.saveAndFlush(this.modelMapper.map(userDto, User.class));
        }

        System.out.println("Successfully imported " + userSeedDtos.length + " users");
    }

    @Override
    public String getUsersAndProducts() {
        List<UserWithSoldProductsDto> userWithSoldProductsDtos = userRepository.findAllUsersWithSoldProducts()
                .stream()
                .map(user -> {
                    // Use ModelMapper to map User to UserWithSoldProductsDto
                    UserWithSoldProductsDto userDto = modelMapper.map(user, UserWithSoldProductsDto.class);

                    // Use ModelMapper to map each Product to ProductDto
                    List<ProductDto> productDtos = user.getSoldProducts().stream()
                            .map(product -> modelMapper.map(product, ProductDto.class))
                            .collect(Collectors.toList());

                    // Create UserSoldProductsInfoDto with the count and list of product DTOs
                    UserSoldProductsInfoDto soldProductsInfo = new UserSoldProductsInfoDto(productDtos.size(), productDtos);
                    userDto.setSoldProducts(soldProductsInfo);

                    return userDto;
                })
                .collect(Collectors.toList());

        // Create the final result DTO containing users count and list of user DTOs
        UsersWithProductsResultDto result = new UsersWithProductsResultDto(userWithSoldProductsDtos.size(), userWithSoldProductsDtos);

        // Convert the result to JSON and return
        return this.gson.toJson(result);
    }

}
