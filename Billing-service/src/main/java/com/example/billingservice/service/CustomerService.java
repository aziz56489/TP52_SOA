package com.example.billingservice.service;

import com.example.billingservice.entity.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="CUSTOMER-SERVICE" , url="${Billing.gateway-url:http://localhost:8888}")
public interface CustomerService {
    @GetMapping("/customers/{id}")
    Customer findCustomerById(@PathVariable("id") Long id);
}