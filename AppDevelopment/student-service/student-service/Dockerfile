# ----------------------------
# Stage 1: Build
# ----------------------------
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom and source
COPY pom.xml ./
COPY src ./src

# Build with retry
RUN mvn clean package -DskipTests || \
    (echo "Build retry 1..." && sleep 5 && mvn clean package -DskipTests) || \
    (echo "Build retry 2..." && sleep 10 && mvn clean package -DskipTests) || \
    (echo "Build retry 3..." && sleep 15 && mvn clean package -DskipTests)

# ----------------------------
# Stage 2: Runtime
# ----------------------------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/student-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]