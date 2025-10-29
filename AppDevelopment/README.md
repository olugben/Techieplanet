# Student Reporting Microservices System

## Overview

A Spring Boot microservices application that tracks student scores, computes statistics, and provides paginated reports via a unified API Gateway. It demonstrates a production-grade microservices architecture with dynamic service discovery, central API gateway routing, inter-service communication, statistical reporting, Swagger API documentation, and Docker-based deployment.

This README will guide you through running the system, understanding its architecture, and interacting with the API endpoints.

---

## Architecture Overview

The system is composed of four microservices:

1. **Eureka Discovery Server** – service registry for dynamic discovery.
2. **API Gateway** – routes all client requests to the appropriate backend service.
3. **Student Service** – manages student data and stores scores.
4. **Reporting Service** – retrieves student data and calculates statistics.

### Architecture Diagram

```
[Client] --> [API Gateway] --> [Student Service]
                           \--> [Reporting Service]
[Student Service & Reporting Service] register with Eureka
```

This diagram shows all client requests go through the **API Gateway**, which uses Eureka for service routing.

---

## Tech Stack

| Layer             | Technology             |
| ----------------- | ---------------------- |
| Language          | Java 17                |
| Framework         | Spring Boot 3.x        |
| Microservices     | Spring Cloud 2025.0.0  |
| Service Discovery | Eureka                 |
| API Gateway       | Spring Cloud Gateway   |
| Database          | PostgreSQL             |
| Persistence       | Spring Data JPA        |
| Communication     | WebClient (Reactive)   |
| API Documentation | Swagger/OpenAPI        |
| Containerization  | Docker, Docker Compose |

---

## Getting Started

### Prerequisites

* Java 17 installed
* Maven 3.9+
* Docker & Docker Compose installed
* Git

### Clone the Repository

```bash
git clone https://github.com/olugben/Techieplanet.git
cd Techieplanet/AppDevelopment
```

### Environment Configuration

1. Copy the sample `.env.example` to `.env`:

```bash
cp .env.example .env
```

2. Fill in your PostgreSQL credentials and other required environment variables.

### Running with Docker Compose

```bash
docker compose up --build
```

This will start the following containers:

* `postgres-student` (PostgreSQL database)
* `eureka-server` (Service registry)
* `student-service` (Handles student data)
* `reporting-service` (Calculates statistics)
* `api-gateway` (Central entry point)

---

## Service Endpoints

### Eureka Discovery Server

* **Dashboard URL:** `http://localhost:8761`
* Use this to verify that `student-service` and `reporting-service` `api-gateway` and are registered and healthy.

### API Gateway

* **Base URL:** `http://localhost:8080`
* All requests should be made through the gateway. It dynamically routes to the backend services using Eureka.

### Student Service (via Gateway)

* **Swagger UI:** `http://localhost:8080/api/students/swagger-ui.html`
* **Create Student Record:**

```bash
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "name": "hammed olugbenga",
  "math": 90,
  "english": 90,
  "physics": 90,
  "chemistry": 90,
  "biology": 90
}

```

* **Retrieve Student Record:**

```bash
GET http://localhost:8080/api/students/1
```

**Response Example:**

```json
{
  "id": 1,
  "name": "John Doe",
  "scores": {
    "math": 85,
    "english": 90,
    "physics": 80
  }
}
```

### Reporting Service (via Gateway)

* **Swagger UI:** `http://localhost:8080/api/reports/swagger-ui.html`
* **Generate Report:**

```bash
GET http://localhost:8080/api/reports?name=John&minScore=50&maxScore=100&page=0&size=10
```

**Response Example:**

```json
{
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "data": [
    {
      "name": "John Doe",
      "scores": [85, 90, 80],
      "mean": 85.0,
      "median": 85.0,
      "mode": 80.0
    }
  ]
}
```

---

## Health Checks

Each service exposes a Spring Boot Actuator endpoint for health monitoring:

```bash
http://localhost:<service-port>/actuator/health
```

Access through the API Gateway as:

```bash
http://localhost:8080/<service-path>/actuator/health
```

* `student-service`: `/api/students/actuator/health`
* `reporting-service`: `/api/reports/actuator/health`

---

## Testing

### Running Tests

```bash
cd student-service
mvn test

cd ../reporting-service
mvn test
```

* **Coverage:** Unit and integration tests cover service logic, API endpoints, validation,

---

## Key Features

* Microservices architecture with **dynamic service discovery**
* Central **API Gateway** routing
* **Swagger/OpenAPI documentation** for easy testing
* **Dockerized** deployment of services
* **Inter-service communication** using WebClient
* **Data validation** and error handling
* **Statistical computations**: mean, median, mode
* **Filtering and pagination** on reports

---

## Notes


* Ensure `.env` contains valid PostgreSQL credentials before starting the system.
* The Eureka dashboard is useful to debug service registration and health.
* Use the Swagger UI of each service to explore all available endpoints interactively.

---