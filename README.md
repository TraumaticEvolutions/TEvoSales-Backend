# TEvoSales-Backend

**Versión:** 1.1.1

TEvoSales-Backend es el backend del marketplace de productos traumatológicos TEvoSales. Proporciona una API RESTful desarrollada en Java utilizando Spring Boot, con autenticación JWT, persistencia en MySQL y validación de datos.

![Image Back Postamn](https://i.imgur.com/X2eMvZY.png)

## Requisitos

- **Java:** 17
- **Spring Boot:** 3.5.0
- **MySQLWorkbench:** 8.0.36

## Instalación

1. **Clona el repositorio:**

   ```bash
   git clone https://github.com/TraumaticEvolutions/TEvoSales-Backend.git
   cd TEvoSales-Backend

   ```

2. **Configura la BBDD**

- Crea una base de datos MySQL.
- Configura las credenciales y URL de la base de datos en el archivo `src/main/resources/application.properties.`
- Por defecto el usuario y contraseña es **root** y el puerto **3306**

3. **Compila el proyecto**

```bash
mvn clean install
```

## Ejecución

```bash
./mvnw spring-boot:run
```

o bien

```bash
mvn spring-boot:run
```

La API estará disponible en el puerto `http://localhost:8080` lista para pruebas con postman

## Autor

- [**Ángel Miguel Aragón** - _@Algol95_](https://github.com/Algol95)
