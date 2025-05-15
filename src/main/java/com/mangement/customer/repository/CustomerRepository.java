package com.mangement.customer.repository;
import com.mangement.customer.model.Customer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
}