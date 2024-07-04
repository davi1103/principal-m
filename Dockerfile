# Utiliza una imagen base de JDK 17 de Eclipse Temurin en Alpine Linux
FROM eclipse-temurin:17-jdk-alpine

# Establece un volumen para permitir el almacenamiento temporal
VOLUME /tmp

# Copia el archivo JAR de tu proyecto al sistema de archivos del contenedor
COPY build/libs/GreenCoom-0.0.1-SNAPSHOT.jar app.jar

# Define el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expone el puerto 8080 para permitir el acceso a la aplicación
EXPOSE 8000
