package com.mangement.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
    
    @NotNull(message = "Annual spend is required")
    private Double annualSpend;
    
    private LocalDateTime lastPurchaseDate;
    
    @Transient
    private String tier;
}