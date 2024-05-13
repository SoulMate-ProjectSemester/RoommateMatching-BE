FROM ubuntu:latest
LABEL authors="joseungbin"

ENTRYPOINT ["top", "-b"]

# Use a base image with JDK installed
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/your-spring-boot-app.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
