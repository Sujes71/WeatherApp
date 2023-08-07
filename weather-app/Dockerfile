# Utiliza una etiqueta específica de la imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo POM y el wrapper de Maven al directorio de trabajo en el contenedor
COPY ./pom.xml ./mvnw ./mvnw.cmd /app/
COPY ./.mvn /app/.mvn

# Dar permisos de ejecución al Maven Wrapper
RUN chmod +x mvnw

# Descargar las dependencias de Maven y hacer una compilación limpia e instalación
RUN ./mvnw clean install -DskipTests

# Copiar el archivo JAR al directorio de trabajo en el contenedor
COPY ./target/weather-app-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 en el contenedor
EXPOSE 8080

# Comando de entrada para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
