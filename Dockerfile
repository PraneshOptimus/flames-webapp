# Start from a base image with Java and Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy everything to the image
COPY . .

# ✅ Add execute permission to mvnw
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Use a smaller runtime image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar from builder
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render sets this automatically)
EXPOSE 8080

# Start the application using the port from environment
ENTRYPOINT ["java", "-jar", "app.jar"]
