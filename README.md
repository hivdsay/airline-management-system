# ✈️ Airline Management System

A comprehensive RESTful API application for managing airline operations including flights, reservations, and user management. Built with Spring Boot 4.0 and modern best practices.

## 📚 Table of Contents

- [About The Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Security](#security)
- [What I Learned](#what-i-learned)

## About The Project

This project was developed as part of my internship to demonstrate backend development skills using Spring Boot. The system manages airline operations with a focus on security, clean code architecture, and comprehensive API documentation.

### Key Objectives:
- Build a production-ready RESTful API
- Implement JWT-based authentication and authorization
- Apply SOLID principles and clean architecture
- Create comprehensive API documentation with Swagger
- Handle errors gracefully with global exception handling

## Features

### Authentication & Authorization
- ✅ User registration with encrypted passwords (BCrypt)
- ✅ JWT-based authentication
- ✅ Role-based access control
- ✅ Secure endpoints with Spring Security

### Flight Management
- ✅ Create, read, update, and delete flights
- ✅ Track flight capacity and availability
- ✅ View all flights or specific flight details

### Reservation Management
- ✅ Create reservations for available flights
- ✅ Check seat availability before booking
- ✅ Update or cancel reservations
- ✅ Prevent overbooking with capacity validation

### User Management
- ✅ CRUD operations for users
- ✅ Secure password storage
- ✅ User profile management

## Technologies Used

### Backend Framework
- **Spring Boot 4.0.0** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **Spring Validation** - Request validation

### Database
- **PostgreSQL** - Primary database
- **Hibernate** - ORM implementation

### Security
- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password encryption
- **JJWT** - JWT implementation library

### Documentation
- **SpringDoc OpenAPI** - API documentation
- **Swagger UI** - Interactive API testing interface

### Development Tools
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management
- **Spring DevTools** - Hot reload during development
- **SLF4J/Logback** - Logging framework

## Architecture

The project follows a layered architecture pattern:

```
├── controller/          # REST endpoints
├── service/            # Business logic
├── repository/         # Database operations
├── entity/             # JPA entities
├── dto/                # Data Transfer Objects
│   ├── auth/          # Authentication DTOs
│   ├── flight/        # Flight DTOs
│   ├── reservation/   # Reservation DTOs
│   ├── user/          # User DTOs
│   ├── error/         # Error response DTOs
│   └── common/        # Common response wrappers
├── exception/         # Custom exceptions
├── security/          # Security configuration
└── config/            # Application configuration
```

### Design Patterns Used:
- **Repository Pattern** - Data access abstraction
- **DTO Pattern** - Separating domain and presentation layers
- **Builder Pattern** - Object construction (via Lombok)
- **Dependency Injection** - Loose coupling between components

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/airline-management-system.git
cd airline-management-system
```

2. **Configure PostgreSQL Database**

Create a new database:
```sql
CREATE DATABASE airline_db;
```

3. **Update application.properties**

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/airline_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
jwt.secret=your-secret-key-here-make-it-long-and-secure
jwt.expiration=86400000
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Quick Start Guide:

1. **Register a new user**
```bash
POST /api/auth/register
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

2. **Login to get JWT token**
```bash
POST /api/auth/login
{
  "email": "user@example.com",
  "password": "password123"
}
```

3. **Use the token**
   - Click "Authorize" in Swagger UI
   - Enter your JWT token
   - Now you can test all protected endpoints!

### Main Endpoints:

#### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

#### Flights
- `GET /api/flights` - Get all flights
- `GET /api/flights/{id}` - Get flight by ID
- `POST /api/flights` - Create new flight
- `PUT /api/flights/{id}` - Update flight
- `DELETE /api/flights/{id}` - Delete flight

#### Reservations
- `GET /api/reservations` - Get all reservations
- `GET /api/reservations/{id}` - Get reservation by ID
- `POST /api/reservations` - Create reservation
- `PUT /api/reservations/{id}` - Update reservation
- `DELETE /api/reservations/{id}` - Cancel reservation

#### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## 🗄 Database Schema

### User Entity
```java
- id (Long, PK)
- email (String, Unique)
- password (String, Encrypted)
- firstName (String)
- lastName (String)
- role (Enum: USER, ADMIN)
- createdAt (LocalDateTime)
```

### Flight Entity
```java
- id (Long, PK)
- flightNumber (String, Unique)
- departureAirport (String)
- arrivalAirport (String)
- departureTime (LocalDateTime)
- arrivalTime (LocalDateTime)
- capacity (Integer)
- availableSeats (Integer)
- price (BigDecimal)
```

### Reservation Entity
```java
- id (Long, PK)
- user (User, FK)
- flight (Flight, FK)
- seatNumber (String)
- status (Enum: CONFIRMED, CANCELLED)
- bookingDate (LocalDateTime)
```

## Security

### Authentication Flow:
1. User registers/logs in with credentials
2. Server validates and returns JWT token
3. Client includes token in Authorization header: `Bearer <token>`
4. Server validates token for each request

### Security Features:
- ✅ Passwords encrypted with BCrypt
- ✅ JWT tokens with expiration
- ✅ Stateless authentication (no server-side sessions)
- ✅ Role-based access control
- ✅ CSRF protection disabled for REST API
- ✅ Public endpoints for auth, protected for business logic

### Protected vs Public Endpoints:
```java
Public (No Authentication):
- POST /api/auth/register
- POST /api/auth/login
- Swagger UI pages

Protected (JWT Required):
- All /api/flights/** endpoints
- All /api/reservations/** endpoints
- All /api/users/** endpoints
```

## Error Handling

The application uses a global exception handler that returns consistent error responses:

```json
{
  "errorCode": "FLIGHT_NOT_FOUND",
  "message": "Flight with ID 123 not found",
  "timestamp": "2024-12-14T15:30:00",
  "details": null
}
```

### Error Codes:
- `USER_NOT_FOUND` - User doesn't exist
- `USER_ALREADY_EXISTS` - Email already registered
- `FLIGHT_NOT_FOUND` - Flight doesn't exist
- `FLIGHT_CAPACITY_ERROR` - No available seats
- `RESERVATION_NOT_FOUND` - Reservation doesn't exist
- `RESERVATION_CONFLICT` - Booking conflict
- `VALIDATION_ERROR` - Request validation failed
- `INVALID_CREDENTIALS` - Wrong username/password
- `ACCESS_DENIED` - Insufficient permissions

##What I Learned

### Technical Skills:
- Building RESTful APIs with Spring Boot
- Implementing JWT authentication and authorization
- Database design and JPA relationships
- Input validation and error handling
- API documentation with OpenAPI/Swagger
- Securing applications with Spring Security
- Clean code principles and best practices

### Architectural Concepts:
- Layered architecture (Controller → Service → Repository)
- Separation of concerns with DTOs
- Dependency injection and IoC
- Exception handling strategies
- Stateless authentication

### Tools & Practices:
- Maven dependency management
- Git version control
- PostgreSQL database management
- Postman/Swagger for API testing
- Lombok for cleaner code
- SLF4J for application logging
