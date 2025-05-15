package com.mangement.customer.controller;

import com.mangement.customer.dto.CustomerDTO;
import com.mangement.customer.model.Customer;
import com.mangement.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
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

    private Customer customerResponse;
    private CustomerDTO customerDto;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = UUID.randomUUID();
        customerResponse = new Customer();
        
        customerDto = new CustomerDTO();
        customerDto.setId(customerId);
        customerDto.setName("Avi Test");
        customerDto.setEmail("test@example.com");
        customerDto.setAnnualSpend(5000.0);
        customerDto.setLastPurchaseDate(LocalDateTime.now().minusMonths(5));
        BeanUtils.copyProperties(customerDto, customerResponse);
        
    }

    @Test
    void testCreateCustomer() {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customerResponse);
        ResponseEntity<Customer> response = customerController.createCustomer(customerDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(customerId)).thenReturn(customerResponse);
        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void testGetCustomerByName() {
        when(customerService.getCustomerByName("Avi Rajguru")).thenReturn(customerResponse);
        ResponseEntity<Customer> response = customerController.getByName("Avi Rajguru");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void testGetCustomerByEmail() {
        when(customerService.getCustomerByEmail("avi.raj@gmail.com")).thenReturn(customerResponse);
        ResponseEntity<Customer> response = customerController.getByEmail("avi.raj@gmail.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(List.of(customerResponse));
        List<Customer> customers = customerController.getAllCustomers();
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.updateCustomer(eq(customerId), any(CustomerDTO.class))).thenReturn(customerResponse);
        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, customerDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomer(customerId);
        ResponseEntity<?> response = customerController.deleteCustomer(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}
