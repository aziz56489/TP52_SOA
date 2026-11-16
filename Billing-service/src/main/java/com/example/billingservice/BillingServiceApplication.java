package com.example.billingservice;

import com.example.billingservice.entity.Bill;
import com.example.billingservice.entity.Customer;
import com.example.billingservice.entity.Product;
import com.example.billingservice.entity.ProductItem;
import com.example.billingservice.repository.BillRepository;
import com.example.billingservice.repository.ProductItemRepository;
import com.example.billingservice.service.CustomerService;
import com.example.billingservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerService customerService,
            InventoryService inventoryService) {
        return args -> {
            try {
                Customer customer = customerService.findCustomerById(1L);
                System.out.println("**********************");
                System.out.println("ID= " + customer.getId());
                System.out.println("Name= " + customer.getName());
                System.out.println("Email= " + customer.getEmail());

                Bill bill = billRepository.save(new Bill(new Date(), customer.getId(), null, null));
                PagedModel<Product> products = inventoryService.findAllProducts();
                products.getContent().forEach(product -> productItemRepository.save(
                        new ProductItem(product.getId(), product, product.getPrice(), 90, bill)));
            } catch (Exception e) {
                // Ne pas arrêter le service si customer/inventory/gateway ne répondent pas
                System.err.println("Initialisation des factures ignorée : " + e.getMessage());
            }
        };
    }
}
