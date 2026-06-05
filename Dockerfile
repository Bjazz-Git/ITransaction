# Step 1: Build the Spring Boot application using Maven and Java 17
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the built JAR file using a lightweight Eclipse Temurin Java runtime
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-jar", "app.jar"]