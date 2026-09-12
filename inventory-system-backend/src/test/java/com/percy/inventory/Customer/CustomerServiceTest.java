package com.percy.inventory.Customer;

import com.percy.inventory.Customer.dto.CreateCustomerRequest;
import com.percy.inventory.Customer.dto.CustomerResponse;
import com.percy.inventory.Customer.dto.UpdateCustomerRequest;
import com.percy.inventory.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_shouldReturnCustomerResponse() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        Customer customer = new Customer(
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        CustomerResponse response = new CustomerResponse(
                1L,
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        when(customerMapper.toEntity(request))
                .thenReturn(customer);

        when(customerRepository.save(customer))
                .thenReturn(customer);

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        // Act
        CustomerResponse result =
                customerService.createCustomer(request);

        // Assert
        assertEquals(response, result);

        verify(customerMapper).toEntity(request);
        verify(customerRepository).save(customer);
        verify(customerMapper).toResponse(customer);
    }

    @Test
    void getCustomerById_shouldReturnCustomer() {
        // Arrange
        Long customerId = 1L;

        Customer customer = new Customer(
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        CustomerResponse response = new CustomerResponse(
                customerId,
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        // Act
        CustomerResponse result =
                customerService.getCustomerById(customerId);

        // Assert
        assertEquals(response, result);
    }

    @Test
    void getCustomerById_shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        Long customerId = 999L;

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getCustomerById(customerId)
        );
    }

    @Test
    void listCustomers_shouldReturnCustomers() {
        // Arrange
        Customer customer1 = new Customer(
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        Customer customer2 = new Customer(
                "Jane Doe",
                "09987654321",
                "jane@example.com",
                "Mandalay"
        );

        CustomerResponse response1 = new CustomerResponse(
                1L,
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        CustomerResponse response2 = new CustomerResponse(
                2L,
                "Jane Doe",
                "09987654321",
                "jane@example.com",
                "Mandalay"
        );

        when(customerRepository.findAll())
                .thenReturn(java.util.List.of(customer1, customer2));

        when(customerMapper.toResponse(customer1))
                .thenReturn(response1);

        when(customerMapper.toResponse(customer2))
                .thenReturn(response2);

        // Act
        java.util.List<CustomerResponse> result =
                customerService.listCustomers();

        // Assert
        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));

        verify(customerRepository).findAll();
        verify(customerMapper).toResponse(customer1);
        verify(customerMapper).toResponse(customer2);
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() {
        // Arrange
        Long customerId = 1L;

        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "John Updated",
                "09999999999",
                "updated@example.com",
                "Mandalay"
        );

        Customer customer = new Customer(
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        CustomerResponse response = new CustomerResponse(
                customerId,
                "John Updated",
                "09999999999",
                "updated@example.com",
                "Mandalay"
        );

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        when(customerRepository.save(customer))
                .thenReturn(customer);

        // Act
        CustomerResponse result =
                customerService.updateCustomer(customerId, request);

        // Assert
        assertEquals(response, result);

        verify(customerRepository).findById(customerId);
        verify(customerMapper).updateEntity(customer, request);
        verify(customerRepository).save(customer);
        verify(customerMapper).toResponse(customer);
    }

    @Test
    void updateCustomer_shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        Long customerId = 999L;

        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "John Updated",
                "09999999999",
                "updated@example.com",
                "Mandalay"
        );

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.updateCustomer(customerId, request)
        );

        verify(customerRepository).findById(customerId);
        verifyNoInteractions(customerMapper);
    }

    @Test
    void deleteCustomer_shouldDeleteCustomer() {
        // Arrange
        Long customerId = 1L;

        Customer customer = new Customer(
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository).findById(customerId);
        verify(customerRepository).delete(customer);
    }

    @Test
    void deleteCustomer_shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        Long customerId = 999L;

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.deleteCustomer(customerId)
        );

        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).delete(any());
    }
}