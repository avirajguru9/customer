package com.mangement.customer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;
    
    @Column(unique = true)
    private String email;
    
    private Double annualSpend;
    
    private LocalDateTime lastPurchaseDate;
    
    @Transient
    private String tier;
}