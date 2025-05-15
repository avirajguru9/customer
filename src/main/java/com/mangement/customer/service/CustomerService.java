package com.mangement.customer.service;

import com.mangement.customer.constant.ApiMessages;
import com.mangement.customer.dto.CustomerDTO;
import com.mangement.customer.exception.CustomerNotFoundException;
import com.mangement.customer.exception.DuplicateCustomerException;
import com.mangement.customer.model.Customer;
import com.mangement.customer.model.Tier;
import com.mangement.customer.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDTO customerDto) {
    	if (customerRepository.findByEmail(customerDto.getEmail()).isPresent()) {
            throw new DuplicateCustomerException(ApiMessages.CUSTOMER_ALREADY_EXISTS);
        }
    	log.info(ApiMessages.CUSTOMER_CREATED);
    	Customer customer = convertToCustomer(customerDto);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(UUID id) {    	
    	log.debug("Looking up customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(ApiMessages.CUSTOMER_NOT_FOUND_ID, id);
                    return new CustomerNotFoundException("Customer not found with id: " + id);
                });
        calculateTier(customer);
        return customer;
    }

    public Customer getCustomerByName(String name) {
    	log.debug("Looking up customer with name: {}", name);
        Customer customer = customerRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error(ApiMessages.CUSTOMER_NOT_FOUND_NAME, name);
                    return new CustomerNotFoundException("Customer not found with name: " + name);
                });
        calculateTier(customer);
        return customer;
    }

    public Customer getCustomerByEmail(String email) {
    	log.debug("Looking up customer with name: {}", email);
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error(ApiMessages.CUSTOMER_NOT_FOUND_EMAIL, email);
                    return new CustomerNotFoundException("Customer not found with email: " + email);
                });
        calculateTier(customer);
        return customer;
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(this::calculateTier);
        return customers;
    }

    public Customer updateCustomer(UUID id, CustomerDTO customerDto) {
    	log.debug("Looking up customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(ApiMessages.CUSTOMER_NOT_FOUND_ID, id);
                    return new CustomerNotFoundException("Customer not found with id: " + id);
                });
        Customer updated = convertToCustomer(customerDto);
        updated.setId(customer.getId());
        log.info(ApiMessages.CUSTOMER_UPDATED);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID id) {
    	log.debug("Looking up customer with id: {}", id);
        customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(ApiMessages.CUSTOMER_NOT_FOUND_ID, id);
                    return new CustomerNotFoundException("Customer not found with id: " + id);
                });
        log.info(ApiMessages.CUSTOMER_DELETED);
        customerRepository.deleteById(id);
    }

    public void calculateTier(Customer customer) {
        LocalDateTime now = LocalDateTime.now();
        Double annualSpend = customer.getAnnualSpend();
        LocalDateTime lastPurchaseDate = customer.getLastPurchaseDate();

        if (annualSpend >= 10000 && lastPurchaseDate != null && 
            lastPurchaseDate.isAfter(now.minusMonths(6))) {
            customer.setTier(Tier.PLATINUM.name());
        } else if (annualSpend >= 1000 && lastPurchaseDate != null && 
                   lastPurchaseDate.isAfter(now.minusMonths(12))) {
            customer.setTier(Tier.GOLD.name());
        } else {
            customer.setTier(Tier.SILVER.name());
        }
    }
    
    private Customer convertToCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setAnnualSpend(dto.getAnnualSpend());
        customer.setLastPurchaseDate(dto.getLastPurchaseDate());
        return customer;
    }
}
