# Stage 1: Build with Maven + Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Stage 2: Run with only Java
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]