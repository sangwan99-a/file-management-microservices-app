# Auth Service

This service is responsible for user authentication and authorization.

## Technologies
- Java / Spring Boot
- Spring Security (for handling authentication and authorization)
- Spring Data JPA
- JWT (JSON Web Tokens) for token-based authentication

## Database
- **PostgreSQL** is used as the primary database to store user credentials (e.g., email, hashed passwords) and roles.
- Connection details are configured in `src/main/resources/application.properties` and typically point to the PostgreSQL instance defined in the main `docker-compose.yml`.
- The `users` table schema is managed by Hibernate (`spring.jpa.hibernate.ddl-auto=update` for development).

## Key Features
- User registration
- User login (issuing JWT upon successful authentication)
- Token validation (can be part of a gateway or resource server)

## Running the Service
- Ensure the PostgreSQL database is running (e.g., via `docker-compose up -d postgres` from the project root).
- The service can be run as a Spring Boot application (e.g., using `mvn spring-boot:run` from this directory or by running the `AuthServiceApplication` main class from an IDE).
