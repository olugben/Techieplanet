# ----------------------------
# Stage 1: Build the application
# ----------------------------
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set workdir
WORKDIR /app

# Fix potential IPv6/TLS issues in Maven
ENV JAVA_TOOL_OPTIONS="-Djava.net.preferIPv4Stack=true"

# Install CA certificates for TLS validation
USER root
RUN apt-get update && apt-get install -y ca-certificates && update-ca-certificates

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml ./

# Pre-download dependencies
RUN mvn dependency:go-offline -B

# Copy all source code
COPY src ./src

# Build the application without tests
RUN mvn clean package -DskipTests

# ----------------------------
# Stage 2: Runtime image
# ----------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy fat jar from build stage
COPY --from=build /app/target/student-service-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Runtime environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/studentdb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=8599
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
