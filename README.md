# Customer Management API

A Spring Boot RESTful API for managing customer data with tier calculation based on annual spend.

## Features
- CRUD operations for customers
- Tier calculation (Silver, Gold, Platinum) based on annual spend
- H2 in-memory database for development
- OpenAPI documentation
- Unit tests

## Technologies
- Java 17
- Spring Boot 3.1.5
- H2 Database
- Maven
- JUnit 5
- Mockito

## Setup
1. Clone the repository
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`

## API Documentation
After starting the application, access the OpenAPI documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html

## H2 Database Console
Access the H2 console at: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:customer_db
- Username: sa
- Password: (leave empty)

### Create Customer

Scenario - 
1. POST /customers: Create a new customer.
 API - http://localhost:8080/api/customers - 
Silver: Annual spend <$1000
{
    "name": "Avi Silver",
    "email": "avi.silver@example.com",
    "annualSpend": 500.0,
    "lastPurchaseDate": "2025-07-15T10:30:00"
}

Gold: Annual spend $1000 and $10000 and purchased within the last 12 months.
{
    "name": "Avi Gold",
    "email": "avi.gold1@example.com",
    "annualSpend": 5000.0,
    "lastPurchaseDate": "2025-07-15T10:30:00"
}

Platinum: Annual spend $10000 and purchased within the last 6 months.
{
    "name": "Avi Platinum",
    "email": "avi.platinum@example.com",
    "annualSpend": 10000.0,
    "lastPurchaseDate": "2025-04-15T10:30:00"
}
