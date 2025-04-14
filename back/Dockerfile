FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package

FROM openjdk:21
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]