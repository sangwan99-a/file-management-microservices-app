# File Service

This service is responsible for handling file uploads, storage, and managing file metadata.

## Technologies
- Java / Spring Boot
- Spring Data JPA

## Build System
- This service now uses Apache Maven for build and dependency management. A `pom.xml` has been added to its root directory.

## Database
- **PostgreSQL** is used to store metadata about the files (e.g., filename, size, type, storage path, uploader information).
- Actual file content is stored in the local file system under the `local-storage` directory (this path is configurable).
- Connection details for PostgreSQL are configured in `src/main/resources/application.properties` and point to the instance defined in the main `docker-compose.yml`.
- The `file_metadata` table schema is managed by Hibernate (`spring.jpa.hibernate.ddl-auto=update` for development).

## Key Features
- File upload: Stores the file in the configured storage and saves metadata to PostgreSQL.
- File metadata retrieval.
- (Future: File download, deletion, listing, etc.)

## API Endpoints
- `POST /api/files/upload`: Uploads a file. Requires `file` (multipart) and optional `userId` (request param).
- `GET /api/files/{fileId}`: Retrieves metadata for a specific file.

## Running the Service
- Ensure the PostgreSQL database is running (e.g., via `docker-compose up -d postgres` from the project root).
- The service can be run as a Spring Boot application:
  - Using Maven: `mvn spring-boot:run` from this directory.
  - By running the `FileServiceApplication` main class from an IDE.
