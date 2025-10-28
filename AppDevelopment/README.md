# Student Reporting Microservices System

## Overview

This project is a **Spring Boot microservices-based application** designed to manage and analyze students' academic scores. It demonstrates modular design, inter-service communication, and microservice orchestration with Docker Compose.

The system is composed of four microservices that work together seamlessly.

---

##  Microservices Architecture

### 1. **Discovery Server**

* Acts as a **Service Registry** for service discovery.
* All other services register here for dynamic endpoint resolution.

### 2. **API Gateway**

* Serves as the **single entry point** to the system.
* Uses **Spring Cloud Gateway** to route incoming requests to appropriate backend services.
* Performs **routing only**.

### 3. **Student Service**

* Handles student score submissions and persistence.
* Accepts subjects  dynamically per student.
* Validates scores (must be between **0 and 100**).
* Stores data in **PostgreSQL** using **Spring Data JPA**.
* Provides **Swagger/OpenAPI documentation** for endpoints.


#### Example Request

```json
{
  "name": "polokio",
  "math": 100,
  "english": 100,
  "physics": 100,
  "chemistry": 100,
  "biology": 100
}
```

#### Example Response

```json
{
  "id": 1,
  "name": "polokio",
  "math": 100.0,
  "english": 100.0,
  "physics": 100.0,
  "chemistry": 100.0,
  "biology": 100.0
}
```

### 4. **Reporting Service**

* Fetches data from **Student Service** using **WebClient** and Eureka discovery.
* Calculates:

    * **Mean**
    * **Median**
    * **Mode**
* Supports filtering by:

    * `name`
    * `minScore`
    * `maxScore`
* Returns results with pagination.

#### Example Response

```json
{
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "data": [
    {
      "name": "polokio",
      "scores": [100.0, 100.0, 100.0, 100.0, 100.0],
      "mean": 100.0,
      "median": 100.0,
      "mode": 100.0
    }
  ]
}
```

---

##  Tech Stack

| Layer            | Technology                   |
| ---------------- | ---------------------------- |
| Language         | Java 17                      |
| Framework        | Spring Boot 3.x              |
| Cloud            | Spring Cloud 2025.0.0        |
| Communication    | WebClient + Eureka Discovery |
| Database         | PostgreSQL                   |
| Persistence      | Spring Data JPA              |
| API Gateway      | Spring Cloud Gateway         |
| Service Registry | Spring Cloud Eureka          |
| Containerization | Docker, Docker Compose       |

---

##  Configuration

* Each service contains its own `pom.xml` and Dockerfile.
* Environment variables are managed via a `.env` file.
* A sample `.env.example` is provided for customization.

---

##  Testing

* Integration tests are implemented in:

    * `student-service`
      Unit tests are implemented in
    * `reporting-service`
* To run tests:

  ```bash
  mvn test
  ```

---

##  Running with Docker Compose

To spin up all services (PostgreSQL + all microservices):
create your own .env or renamed the provided .env.example and fill with appropriate postgres credential
```bash
docker compose up --build
```

This will launch:

* `postgres-student`
* `eureka-server`
* `student-service`
* `reporting-service`
* `api-gateway`

---

##  API Endpoints Summary

| Service               | Base Path       | Example Endpoints                                                   |
| --------------------- | --------------- | ------------------------------------------------------------------- |
| **Student Service**   | `/api/students` | `POST /api/students` → Create Student Record                        |
|                       |                 | `GET /api/students/{id}` → Retrieve Student                         |
| **Reporting Service** | `/api/reports`  | `GET /api/reports?name=John&minScore=50&maxScore=90&page=0&size=10` |

All requests can  be made **through the API Gateway**, which routes automatically via Eureka.

---

## Health Check

Each service exposes a standard health endpoint:

```
/actuator/health
```

---

##  Key Features Demonstrated

* Microservices architecture
* Dynamic service discovery (Eureka)
* Central API Gateway routing
* Dockerized service orchestration
* Inter-service communication with WebClient
* Data validation and error handling
* Statistical computations (Mean, Median, Mode)
* Filtering and pagination on computed reports





