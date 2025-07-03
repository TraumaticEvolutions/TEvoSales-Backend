# Usa una imagen base de Java 17
FROM eclipse-temurin:17-jdk

# Crea el directorio de trabajo
WORKDIR /app

# Copia el JAR generado al contenedor (ajusta el nombre si cambias la versión)
COPY target/tevosales-backend-1.1.1.jar app.jar

# Expón el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]