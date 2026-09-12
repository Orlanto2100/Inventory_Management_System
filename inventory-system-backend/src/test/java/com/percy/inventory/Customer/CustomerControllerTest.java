package com.percy.inventory.Customer;

import com.percy.inventory.Customer.dto.CreateCustomerRequest;
import com.percy.inventory.Customer.dto.CustomerResponse;
import com.percy.inventory.Customer.dto.UpdateCustomerRequest;
import com.percy.inventory.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void createCustomer_shouldReturnCreated() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest(
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

        when(customerService.createCustomer(request))
                .thenReturn(response);

        mockMvc.perform(post("/api/customers")
                        .with(user("testuser"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "John Doe",
                                "phone": "09123456789",
                                "email": "john@example.com",
                                "address": "Yangon"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.phone").value("09123456789"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.address").value("Yangon"));
    }

    @Test
    void getCustomerById_shouldReturnCustomer() throws Exception {
        CustomerResponse response = new CustomerResponse(
                1L,
                "John Doe",
                "09123456789",
                "john@example.com",
                "Yangon"
        );

        when(customerService.getCustomerById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/customers/1")
                        .with(user("testuser")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.phone").value("09123456789"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.address").value("Yangon"));
    }

    @Test
    void getCustomerById_shouldReturnNotFound() throws Exception {
        when(customerService.getCustomerById(1L))
                .thenThrow(new ResourceNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/customers/1")
                        .with(user("testuser")))
                .andExpect(status().isNotFound());
    }

    @Test
    void listCustomers_shouldReturnCustomers() throws Exception {
        List<CustomerResponse> responses = List.of(
                new CustomerResponse(
                        1L,
                        "John Doe",
                        "09123456789",
                        "john@example.com",
                        "Yangon"
                ),
                new CustomerResponse(
                        2L,
                        "Jane Doe",
                        "09876543210",
                        "jane@example.com",
                        "Mandalay"
                )
        );

        when(customerService.listCustomers())
                .thenReturn(responses);

        mockMvc.perform(get("/api/customers")
                        .with(user("testuser")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].customerId").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() throws Exception {
        CustomerResponse response = new CustomerResponse(
                1L,
                "John Updated",
                "09111111111",
                "updated@example.com",
                "Mandalay"
        );

        when(customerService.updateCustomer(
                1L,
                new UpdateCustomerRequest(
                        "John Updated",
                        "09111111111",
                        "updated@example.com",
                        "Mandalay"
                )
        )).thenReturn(response);

        mockMvc.perform(patch("/api/customers/1")
                        .with(user("testuser"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "John Updated",
                                "phone": "09111111111",
                                "email": "updated@example.com",
                                "address": "Mandalay"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.phone").value("09111111111"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.address").value("Mandalay"));
    }

    @Test
    void deleteCustomer_shouldReturnNoContent() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/1")
                        .with(user("testuser"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void createCustomer_shouldReturnBadRequestWhenInvalid() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .with(user("testuser"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "",
                            "phone": "",
                            "email": "invalid-email",
                            "address": "Yangon"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCustomer_shouldReturnBadRequestWhenInvalid() throws Exception {
        mockMvc.perform(patch("/api/customers/1")
                        .with(user("testuser"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "John Doe",
                            "phone": "09123456789",
                            "email": "invalid-email",
                            "address": "Yangon"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
}