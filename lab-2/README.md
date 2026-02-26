# Pet Clinic Backend
Spring Boot REST API with PostgreSQL and Spring Security.
## Setup
1. Create database: `createdb lab2_db`
2. Copy `.env.example` to `.env` and configure
3. Run: `mvn spring-boot:run`
## Endpoints
- Register: `POST /api/auth/register`
- Login: `POST /api/auth/login`
- Owners: `GET /api/owners` (ADMIN only)
- Pets: `GET /api/pets` (authenticated)
Swagger: http://localhost:8080/swagger-ui/index.html
