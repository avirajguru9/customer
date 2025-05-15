package com.mangement.customer.constant;

public class ApiMessages {
    // Success messages
    public static final String CUSTOMER_CREATED = "Customer created successfully";
    public static final String CUSTOMER_UPDATED = "Customer updated successfully";
    public static final String CUSTOMER_DELETED = "Customer deleted successfully";
    public static final String CUSTOMER_FOUND = "Customer retrieved successfully";
    
    // Error messages
    public static final String CUSTOMER_ALREADY_EXISTS = "Customer with email already exists";
    public static final String CUSTOMER_NOT_FOUND_ID = "Customer not found with id: {}";
    public static final String CUSTOMER_NOT_FOUND_NAME = "Customer not found with name: {}";
    public static final String CUSTOMER_NOT_FOUND_EMAIL = "Customer not found with email: {}";
}