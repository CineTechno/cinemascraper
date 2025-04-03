# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

# Ensure the wrapper is executable
RUN chmod +x mvnw

# Run the build
RUN bash ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk

WORKDIR /app
#just need to add for git#
# Use exact JAR name — confirmed in your target folder
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Run the app
CMD ["java", "-jar", "app.jar"]