# Use a minimal Java 17 runtime image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR into the container
COPY target/Wellness-Tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Spring Boot runs on (default: 8080)
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]