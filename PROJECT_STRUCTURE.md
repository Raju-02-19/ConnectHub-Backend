# Backend Project Structure Documentation

## Project Overview
This is a Spring Boot backend application for a chat application (`chatapp-backend`). It provides REST APIs for user authentication, user management, and messaging functionality.

---

## Directory Structure

```
backend/
├── pom.xml                          # Maven configuration file with dependencies
├── mvnw & mvnw.cmd                  # Maven wrapper scripts (Unix & Windows)
├── HELP.md                          # Help documentation
├── PROJECT_STRUCTURE.md             # This file
│
├── src/
│   ├── main/
│   │   ├── java/com/chatapp/backend/
│   │   │   ├── BackendApplication.java           # Spring Boot main application class
│   │   │   │
│   │   │   ├── controller/                       # REST API Controllers
│   │   │   │   ├── AuthController.java          # Authentication endpoints
│   │   │   │   ├── MessageController.java       # Messaging endpoints
│   │   │   │   └── UserController.java          # User management endpoints
│   │   │   │
│   │   │   ├── dto/                             # Data Transfer Objects
│   │   │   │   ├── LoginRequest.java           # Login request payload
│   │   │   │   └── RegisterRequest.java        # Registration request payload
│   │   │   │
│   │   │   ├── model/                           # Entity/Domain Models
│   │   │   │   ├── Message.java                # Message entity
│   │   │   │   └── User.java                   # User entity
│   │   │   │
│   │   │   ├── repository/                      # Data Access Layer
│   │   │   │   ├── MessageRepository.java      # Message database operations
│   │   │   │   └── UserRepository.java         # User database operations
│   │   │   │
│   │   │   └── service/                         # Business Logic Layer
│   │   │       ├── AuthService.java            # Authentication logic
│   │   │       └── UserService.java            # User management logic
│   │   │
│   │   └── resources/
│   │       ├── application.properties           # Application configuration
│   │       ├── static/                          # Static files (CSS, JS, images)
│   │       └── templates/                       # HTML templates
│   │
│   └── test/
│       └── java/com/chatapp/backend/
│           └── BackendApplicationTests.java    # Unit tests
│
└── target/                                      # Build output directory
    ├── classes/                                 # Compiled classes
    ├── generated-sources/                       # Generated source files
    ├── generated-test-sources/                  # Generated test sources
    ├── maven-status/                            # Maven build metadata
    └── test-classes/                            # Compiled test classes
```

---

## Module Descriptions

### 1. **Controller Layer** (`controller/`)
Handles HTTP requests and responses.

| File | Purpose |
|------|---------|
| `AuthController.java` | Manages user login and authentication endpoints |
| `MessageController.java` | Handles message creation, retrieval, and deletion |
| `UserController.java` | Manages user profile operations |

### 2. **DTO Layer** (`dto/`)
Data Transfer Objects for API request/response mapping.

| File | Purpose |
|------|---------|
| `LoginRequest.java` | Defines the structure of login requests |
| `RegisterRequest.java` | Defines the structure of registration requests |

### 3. **Model Layer** (`model/`)
JPA entity classes representing database tables.

| File | Purpose |
|------|---------|
| `User.java` | User entity with properties like username, password, email, etc. |
| `Message.java` | Message entity with properties like content, sender, recipient, timestamp, etc. |

### 4. **Repository Layer** (`repository/`)
Data access layer using Spring Data JPA.

| File | Purpose |
|------|---------|
| `UserRepository.java` | CRUD operations and custom queries for User entity |
| `MessageRepository.java` | CRUD operations and custom queries for Message entity |

### 5. **Service Layer** (`service/`)
Business logic and service implementations.

| File | Purpose |
|------|---------|
| `AuthService.java` | Authentication logic, token generation, validation |
| `UserService.java` | User-related business operations, profile management |

### 6. **Main Application** (`BackendApplication.java`)
The Spring Boot application entry point with the `main()` method.

### 7. **Configuration** (`resources/application.properties`)
Application settings including:
- Database configuration
- Server port
- JPA/Hibernate settings
- Logging levels
- Custom application properties

---

## Architecture Pattern

This project follows the **Layered Architecture** pattern:

```
┌─────────────────────────┐
│   Controller Layer      │ ← REST endpoints
├─────────────────────────┤
│   Service Layer         │ ← Business logic
├─────────────────────────┤
│   Repository Layer      │ ← Data access
├─────────────────────────┤
│   Database              │ ← Data persistence
└─────────────────────────┘
```

---

## Build & Deployment

- **Build Tool**: Maven
- **Build Output**: `target/` directory
- **Compiled Classes**: `target/classes/`
- **Test Classes**: `target/test-classes/`

### Commands
```bash
./mvnw clean build          # Build the project (Unix)
mvnw.cmd clean build        # Build the project (Windows)
./mvnw spring-boot:run      # Run the application
```

---

## Key Technologies

- **Framework**: Spring Boot
- **Database Access**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Language**: Java

---

## Dependencies

Check `pom.xml` for all project dependencies, including:
- Spring Boot Web
- Spring Data JPA
- Database Driver (H2, MySQL, PostgreSQL, etc.)
- Validation libraries
- Testing frameworks (JUnit, Mockito)

---

## Development Workflow

1. **Frontend Request** → Hits a **Controller** endpoint
2. **Controller** → Calls the appropriate **Service** method
3. **Service** → Contains business logic and calls **Repository** methods
4. **Repository** → Executes database queries using **Models**
5. **Response** flows back through layers as **DTOs**

---

## Testing

Unit tests are located in `src/test/java/`. Tests verify:
- API endpoint behavior
- Business logic correctness
- Data persistence

---

## Notes

- Ensure `application.properties` is configured with correct database credentials before running
- All REST endpoints should be documented (consider adding Swagger/Springdoc)
- Follow consistent naming conventions across layers
- Keep business logic in services, not in controllers
