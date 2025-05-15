Customer Management Project


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
