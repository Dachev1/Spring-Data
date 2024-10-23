package org.example.car_dealer.service.impl;

import com.google.gson.Gson;
import org.example.car_dealer.data.entities.Customer;
import org.example.car_dealer.data.repositories.CustomerRepository;
import org.example.car_dealer.service.CustomerService;
import org.example.car_dealer.service.dtos.exportDtos.car.CarSaleDetailDto;
import org.example.car_dealer.service.dtos.exportDtos.customer.CustomerOrderedDto;
import org.example.car_dealer.service.dtos.exportDtos.customer.CustomerTotalSalesDto;
import org.example.car_dealer.service.dtos.exportDtos.sale.SaleDto;
import org.example.car_dealer.service.dtos.importDtos.customer.CustomerDto;
import org.example.car_dealer.utli.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String FILE_PATH = "src/main/resources/json_import_data/customers.json";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidatorComponent validatorComponent, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
        this.gson = gson;
    }

    @Override
    public void seedCustomers() throws IOException {
        if (this.customerRepository.count() != 0) {
            return;
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            CustomerDto[] customerDtos = this.gson.fromJson(reader, CustomerDto[].class);

            for (CustomerDto customerDto : customerDtos) {
                if (!this.validatorComponent.isValid(customerDto)) {
                    this.validatorComponent.validate(customerDto).forEach(error -> System.out.println(error.getMessage()));
                    continue;
                }

                Customer customer = this.modelMapper.map(customerDto, Customer.class);

                if (customerDto.getBirthDate() != null) {
                    customer.setBirthDate(LocalDate.parse(customerDto.getBirthDate(), DATE_TIME_FORMATTER));
                }

                this.customerRepository.saveAndFlush(customer);
            }
        }
    }

    @Override
    public String getOrderedCustomersWithSalesAsJson() {
        List<Customer> customers = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc();

        List<CustomerOrderedDto> orderedCustomerDtos = customers
                .stream()
                .map(customer -> {
                    CustomerOrderedDto dto = this.modelMapper.map(customer, CustomerOrderedDto.class);
                    List<SaleDto> saleDtos = customer.getSales()
                            .stream()
                            .map(sale -> {
                                SaleDto saleDto = this.modelMapper.map(sale, SaleDto.class);

                                // Manually map the car details including total price
                                CarSaleDetailDto carDto = this.modelMapper.map(sale.getCar(), CarSaleDetailDto.class);
                                carDto.setTotalPrice(sale.getCar().getTotalPrice());
                                saleDto.setCar(carDto);

                                return saleDto;
                            })
                            .toList();

                    dto.setSales(saleDtos);
                    return dto;
                })
                .toList();

        return this.gson.toJson(orderedCustomerDtos);
    }

    @Override
    public String getTotalSalesByCustomerAsJson() {
        List<Customer> customers = customerRepository.findAllWithSales();

        List<CustomerTotalSalesDto> customerSalesDtos = customers.stream()
                .filter(customer -> !customer.getSales().isEmpty())
                .map(customer -> {
                    double totalMoneySpent = customer.getSales().stream()
                            .mapToDouble(sale -> sale.getCar().getTotalPrice() * (1 - sale.getDiscount() / 100))
                            .sum();

                    totalMoneySpent = Math.round(totalMoneySpent * 100.0) / 100.0;

                    CustomerTotalSalesDto dto = modelMapper.map(customer, CustomerTotalSalesDto.class);
                    dto.setSpentMoney(totalMoneySpent);
                    dto.setBoughtCars(customer.getSales().size());

                    return dto;
                })
                .sorted(Comparator.comparingDouble(CustomerTotalSalesDto::getSpentMoney).reversed()
                        .thenComparingLong(CustomerTotalSalesDto::getBoughtCars).reversed())
                .toList();

        return gson.toJson(customerSalesDtos);
    }
}
