package org.example.car_dealer.service.impl;

import com.google.gson.Gson;
import org.example.car_dealer.data.entities.Car;
import org.example.car_dealer.data.entities.Customer;
import org.example.car_dealer.data.entities.Sale;
import org.example.car_dealer.data.repositories.CarRepository;
import org.example.car_dealer.data.repositories.CustomerRepository;
import org.example.car_dealer.data.repositories.SaleRepository;
import org.example.car_dealer.service.SaleService;
import org.example.car_dealer.service.dtos.exportDtos.car.CarInfoDto;
import org.example.car_dealer.service.dtos.exportDtos.sale.SaleWithDiscountDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final Gson gson;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, Gson gson) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.gson = gson;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() != 0) {
            return;
        }

        List<Car> cars = this.carRepository.findAll();
        List<Customer> customers = this.customerRepository.findAll();
        List<Integer> discounts = Arrays.asList(0, 5, 10, 15, 20, 30, 40, 50);

        for (int i = 0; i < 100; i++) {
            Car randomCar = cars.get(ThreadLocalRandom.current().nextInt(cars.size()));
            Customer randomCustomer = customers.get(ThreadLocalRandom.current().nextInt(customers.size()));
            int randomDiscount = discounts.get(ThreadLocalRandom.current().nextInt(discounts.size()));

            Sale sale = new Sale();
            sale.setCar(randomCar);
            sale.setCustomer(randomCustomer);
            sale.setDiscount(randomDiscount);

            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public String getSalesWithAppliedDiscountAsJson() {
        List<Sale> sales = saleRepository.findAll();

        List<SaleWithDiscountDto> saleDtos = sales.stream().map(sale -> {
            SaleWithDiscountDto dto = new SaleWithDiscountDto();

            CarInfoDto carDetails = new CarInfoDto();
            carDetails.setMake(sale.getCar().getMake());
            carDetails.setModel(sale.getCar().getModel());
            carDetails.setTravelledDistance(sale.getCar().getTravelledDistance());
            dto.setCar(carDetails);

            dto.setCustomerName(sale.getCustomer().getName());

            double price = sale.getCar().getTotalPrice();
            double discount = sale.getDiscount();
            double priceWithDiscount = price * (1 - discount / 100);

            dto.setDiscount(discount);
            dto.setPrice(Math.round(price * 100.0) / 100.0);
            dto.setPriceWithDiscount(Math.round(priceWithDiscount * 100.0) / 100.0);

            return dto;
        }).toList();

        return this.gson.toJson(saleDtos);
    }

}
