# Use Maven with Java 21 to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy project files
COPY . .

# Give execute permission to Maven wrapper (if present)
RUN chmod +x mvnw

# Build the application (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# -------------------------
# Run stage: slim JDK to run the built app
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
