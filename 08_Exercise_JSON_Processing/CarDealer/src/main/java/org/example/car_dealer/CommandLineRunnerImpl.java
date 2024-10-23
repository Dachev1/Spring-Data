package org.example.car_dealer;

import org.example.car_dealer.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Import

        // Step 1: Seed Suppliers
//        this.supplierService.seedSuppliers();
//        System.out.println("Suppliers seeded.");
//
//        // Step 2: Seed Parts
//        this.partService.seedParts();
//        System.out.println("Parts seeded.");
//
//        // Step 3: Seed Cars
//        this.carService.seedCars();
//        System.out.println("Cars seeded.");
//
//        // Step 4: Seed Customers
//        this.customerService.seedCustomers();
//        System.out.println("Customers seeded.");
//
//        // Step 5: Seed Sales
//        this.saleService.seedSales();
//        System.out.println("Sales seeded.");

        // Export

//        String jsonResult = customerService.getOrderedCustomersWithSalesAsJson();
//        System.out.println(jsonResult);

//        String carsByMake = this.carService.getALlCarsByMake("Toyota");
//        System.out.println(carsByMake);

//        System.out.println(this.supplierService.getLocalSuppliersAsJson());
//        System.out.println(this.carService.getCarsWithTheirPartsAsJson());

//        System.out.println(this.customerService.getTotalSalesByCustomerAsJson());

//        System.out.println(this.saleService.getSalesWithAppliedDiscountAsJson());
    }
}
