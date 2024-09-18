# Use an OpenJDK base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app
COPY . /app

# Copy the JAR file from your build into the container
RUN ./mvnw  -B -DskipTests clean package

# Expose port 8080
EXPOSE 8080

CMD ["java", "-jar", "target/MarketSpy-0.0.1-SNAPSHOT.jar"]


