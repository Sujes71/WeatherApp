# Fase de compilación
FROM maven:3.9.3-eclipse-temurin-17-alpine AS build

WORKDIR /home/app

COPY pom.xml /home/app
COPY .mvn /home/app/.mvn
COPY src /home/app/src

RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Fase de producción
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /home/app/target/weather-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]