# Use an OpenJDK image to build the app
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

# Use a smaller base image for running the app
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/Wellness-Tracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]