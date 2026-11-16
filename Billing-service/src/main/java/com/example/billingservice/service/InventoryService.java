package com.example.billingservice.service;

import com.example.billingservice.entity.Product;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="INVENTORY-SERVICE" , url="${Billing.gateway-url:http://localhost:8888}")
public interface InventoryService {
    @GetMapping("/products/{id}")
    Product findProductById(@PathVariable("id") Long id);

    @GetMapping("/products")
    PagedModel<Product> findAllProducts();

}
