package com.example.billingservice.repository;

import java.util.Collection;
import java.util.Date;

import com.example.billingservice.entity.Bill;
import com.example.billingservice.entity.ProductItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;

@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Projection(name = "fullBill", types = Bill.class)
    interface BillProjection {
        Long getId();
        Date getBillingDate();
        Long getCustomerID();
        Collection<ProductItem> getProductItems();
    }
}
