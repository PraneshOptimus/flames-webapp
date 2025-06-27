# Step 1: Use an official Java 17 image as base
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy the project files into the image
COPY . .

# Step 4: Build the app using Maven (make sure mvnw is present)
RUN ./mvnw clean package -DskipTests

# Step 5: Expose the port your Spring Boot app uses
EXPOSE 8080

# Step 6: Run the JAR file
CMD ["java", "-jar", "target/flamesProject-0.0.1-SNAPSHOT.jar"]
