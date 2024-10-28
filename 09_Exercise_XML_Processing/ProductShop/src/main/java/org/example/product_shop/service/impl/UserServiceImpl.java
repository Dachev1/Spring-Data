package org.example.product_shop.service.impl;

import org.example.product_shop.data.entities.User;
import org.example.product_shop.data.repositories.UserRepository;
import org.example.product_shop.service.UserService;
import org.example.product_shop.service.dtos.exportDtos.product.ProductListExportDto;
import org.example.product_shop.service.dtos.exportDtos.product.SoldProductDto;
import org.example.product_shop.service.dtos.exportDtos.product.SoldProductExportDto;
import org.example.product_shop.service.dtos.exportDtos.product.SoldProductsDto;
import org.example.product_shop.service.dtos.exportDtos.user.UserProductsExportRootDto;
import org.example.product_shop.service.dtos.exportDtos.user.UserWithProductsExportDto;
import org.example.product_shop.service.dtos.exportDtos.user.UserWithSoldProductsDto;
import org.example.product_shop.service.dtos.exportDtos.user.UserWithSoldProductsRootDto;
import org.example.product_shop.service.dtos.importDtos.user.UserDto;
import org.example.product_shop.service.dtos.importDtos.user.UserRootDto;
import org.example.product_shop.util.validation.ValidatorComponent;
import org.example.product_shop.util.xmlmapper.XMLMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final static String FILE_IMPORT_PATH = "src/main/resources/xml/imports/users.xml";
    private final static String FIRST_FILE_EXPORT_PATH = "src/main/resources/xml/exports/users-sold-products.xml";
    private final static String SECOND_FILE_EXPORT_PATH = "src/main/resources/xml/exports/users-and-products.xml";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final XMLMapperService xmlMapperService;
    private final ValidatorComponent validatorComponent;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, XMLMapperService xmlMapperService, ValidatorComponent validatorComponent) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.xmlMapperService = xmlMapperService;
        this.validatorComponent = validatorComponent;
    }

    @Override
    public void seedUsers() throws Exception {
        if (this.userRepository.count() == 0) {
            UserRootDto userRootDto = this.xmlMapperService.unmarshal(UserRootDto.class, FILE_IMPORT_PATH);

            for (UserDto userDto : userRootDto.getUserDtoList()) {
                if (!this.validatorComponent.isValid(userDto)) {
                    this.validatorComponent.validate(userDto).forEach(System.out::println);
                    continue;
                }

                this.userRepository.saveAndFlush(this.modelMapper.map(userDto, User.class));
            }
        }
    }

    @Override
    public void exportUsersWithSoldProducts() throws Exception {
        List<UserWithSoldProductsDto> userDtos = this.userRepository.findAllWithSoldProducts().stream()
                .map(user -> {
                    UserWithSoldProductsDto userDto = this.modelMapper.map(user, UserWithSoldProductsDto.class);

                    List<SoldProductDto> soldProducts = user.getSoldProducts().stream()
                            .filter(product -> product.getBuyer() != null)
                            .map(product -> this.modelMapper.map(product, SoldProductDto.class))
                            .toList();

                    SoldProductsDto soldProductsDto = new SoldProductsDto();
                    soldProductsDto.setProducts(soldProducts);

                    userDto.setSoldProducts(soldProductsDto);
                    return userDto;
                })
                .toList();

        UserWithSoldProductsRootDto rootDto = new UserWithSoldProductsRootDto();
        rootDto.setUsers(userDtos);

        this.xmlMapperService.marshal(rootDto, FIRST_FILE_EXPORT_PATH);
    }

    @Override
    public void exportUsersWithSalesData() throws Exception {
        List<UserWithProductsExportDto> userDtos = this.userRepository.findAllWithSoldProducts().stream()
                .map(user -> {
                    UserWithProductsExportDto userDto = this.modelMapper.map(user, UserWithProductsExportDto.class);

                    List<SoldProductExportDto> products = user.getSoldProducts().stream()
                            .filter(product -> product.getBuyer() != null)
                            .map(product -> this.modelMapper.map(product, SoldProductExportDto.class))
                            .toList();

                    ProductListExportDto soldProducts = new ProductListExportDto();
                    soldProducts.setCount(products.size());
                    soldProducts.setProducts(products);

                    userDto.setSoldProducts(soldProducts);

                    return userDto;
                })
                .toList();

        UserProductsExportRootDto rootDto = new UserProductsExportRootDto();
        rootDto.setCount(userDtos.size());
        rootDto.setUsers(userDtos);

        this.xmlMapperService.marshal(rootDto, SECOND_FILE_EXPORT_PATH);
    }
}
