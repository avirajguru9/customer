package com.mangement.customer.service;

import com.mangement.customer.exception.DuplicateCustomerException;
import com.mangement.customer.model.Customer;
import com.mangement.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private CustomerService customerService;
    
    @Test
    void createCustomer_shouldSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("Test");
        customer.setEmail("test@example.com");
        customer.setAnnualSpend(500.0);
        
        when(customerRepository.save(customer)).thenReturn(customer);
        
        Customer result = customerService.createCustomer(customer);
        
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
    
    @Test
    void calculateTier_shouldReturnCorrectTier() {
        Customer customer = new Customer();
        customer.setAnnualSpend(500.0);
        customerService.calculateTier(customer);
        assertEquals("SILVER", customer.getTier());
        
        customer.setAnnualSpend(1500.0);
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(10));
        customerService.calculateTier(customer);
        assertEquals("GOLD", customer.getTier());
        
        customer.setAnnualSpend(15000.0);
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(3));
        customerService.calculateTier(customer);
        assertEquals("PLATINUM", customer.getTier());
    }
    
    @Test
    void shouldThrowWhenEmailAlreadyExists() {

    	Customer customer = new Customer();
        customer.setName("Test");
        customer.setEmail("test@example.com");
        customer.setAnnualSpend(500.0);
    	when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));

        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(customer));
    }
}