# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

# 🔥 Make the Maven wrapper script executable
RUN chmod +x mvnw

# Now build the app
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]