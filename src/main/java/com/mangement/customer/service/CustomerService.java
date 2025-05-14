package com.mangement.customer.service;

import com.mangement.customer.model.Customer;
import com.mangement.customer.model.Tier;
import com.mangement.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(this::calculateTier);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(this::calculateTier);
        return customers;
    }

    public Optional<Customer> updateCustomer(UUID id, Customer customerDetails) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
            customer.setAnnualSpend(customerDetails.getAnnualSpend());
            customer.setLastPurchaseDate(customerDetails.getLastPurchaseDate());
            return customerRepository.save(customer);
        });
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

    private void calculateTier(Customer customer) {
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
}
