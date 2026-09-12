package com.percy.inventory.Customer;

import com.percy.inventory.Customer.dto.CreateCustomerRequest;
import com.percy.inventory.Customer.dto.CustomerResponse;
import com.percy.inventory.Customer.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    public Customer toEntity(CreateCustomerRequest request) {
        return new Customer(
                request.name(),
                request.phone(),
                request.email(),
                request.address()
        );
    }

    public void updateEntity(
            Customer customer,
            UpdateCustomerRequest request) {

        customer.update(
                request.name(),
                request.phone(),
                request.email(),
                request.address()
        );
    }
}