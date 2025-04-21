# Use a Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy all files to build context
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Use a smaller image to run the app
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/Wellness-Tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]