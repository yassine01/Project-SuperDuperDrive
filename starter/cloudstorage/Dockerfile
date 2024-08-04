# Use the official OpenJDK 11 image with JDK as the base image
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven executable to the container
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Download all dependencies. This step is used to cache dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the project source code
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# Set the working directory to the location of the JAR file
WORKDIR /app/target

# Expose the port the application runs on
EXPOSE 8080

# Set the entry point to run the jar file
ENTRYPOINT ["java", "-jar", "cloudstorage-0.0.1.jar"]