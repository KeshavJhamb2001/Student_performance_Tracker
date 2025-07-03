# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven build jar to container
COPY target/Student_performance_Tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Railway uses (fallback to 8080)
EXPOSE 8080

# Entry point
CMD ["java", "-jar", "Student_Performance_Tracker-0.0.1-SNAPSHOT.jar"]
