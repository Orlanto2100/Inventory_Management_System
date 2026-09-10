package com.percy.inventory.Customer;

import com.percy.inventory.Customer.dto.CreateCustomerRequest;
import com.percy.inventory.Customer.dto.CustomerResponse;
import com.percy.inventory.Customer.dto.UpdateCustomerRequest;

public class CustomerMapper {

    public static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    public static Customer toEntity(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());

        return customer;
    }

    public static void updateEntity(Customer customer, UpdateCustomerRequest request) {
        if (request.getName() != null) {
            customer.setName(request.getName());
        }

        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
    }
}