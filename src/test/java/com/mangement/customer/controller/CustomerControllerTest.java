package com.mangement.customer.controller;

import com.mangement.customer.model.Customer;
import com.mangement.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = UUID.randomUUID();
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Avi Test");
        customer.setEmail("test@example.com");
        customer.setAnnualSpend(5000.0);
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(5));
    }

    @Test
    void testCreateCustomer() {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.createCustomer(customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerByName() {
        when(customerService.getCustomerByName("Avi Rajguru")).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.getByName("Avi Rajguru");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerByEmail() {
        when(customerService.getCustomerByEmail("avi.raj@gmail.com")).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.getByEmail("avi.raj@gmail.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(List.of(customer));
        List<Customer> customers = customerController.getAllCustomers();
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.updateCustomer(eq(customerId), any(Customer.class))).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomer(customerId);
        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}
