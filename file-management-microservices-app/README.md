# File Management Microservices App

This project is a microservices-based application for managing files.

## Overview

The system consists of several microservices, including:
- **Auth Service:** Manages user authentication and authorization.
- **File Service:** Handles file uploads, storage, and metadata management.
- ... (other services like config, eureka, gateway, etc.)

## Technologies

- Java / Spring Boot (for most backend services)
- React (for frontend)
- Docker
- RabbitMQ (for messaging)
- MongoDB (for specific services/data)
- **PostgreSQL** (for relational data, e.g., user credentials in Auth Service, file metadata in File Service)

## Setup

1.  Ensure Docker and Docker Compose are installed.
2.  Run `docker-compose up -d` from the `file-management-microservices-app` directory to start the necessary infrastructure (including PostgreSQL, MongoDB, RabbitMQ).
3.  Build and run each microservice. For Maven-based services (like Auth Service, File Service), you can typically use `mvn spring-boot:run`.

### PostgreSQL
The system uses PostgreSQL for storing:
- User information in the `auth-service`.
- File metadata in the `file-service`.

The PostgreSQL instance is managed via Docker Compose (`docker-compose.yml`). Connection details (defaults):
- **Host:** `localhost`
- **Port:** `5432`
- **Database Name:** `filedb`
- **Username:** `user`
- **Password:** `password`
