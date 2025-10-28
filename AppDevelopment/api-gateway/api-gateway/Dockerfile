# -------- Stage 1: Build --------
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and force TLS + offline dependencies
COPY pom.xml .

# Ensure Maven uses TLSv1.2/1.3 to avoid 'bad_record_mac'
#RUN mvn -Dhttps.protocols=TLSv1.2,TLSv1.3 dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
