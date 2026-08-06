# 👨‍💼 Gestión de Empleados API

API REST desarrollada con **Java 21** y **Spring Boot 4** para la gestión de empleados y departamentos.

El proyecto implementa una arquitectura por capas utilizando Spring Boot, Spring Data JPA y PostgreSQL, incorporando buenas prácticas como el uso de DTOs, validación de datos, manejo global de excepciones, paginación y documentación interactiva con Swagger/OpenAPI.

## 🚀 Características

* CRUD completo de empleados.
* CRUD completo de departamentos.
* Búsqueda de empleados:

    * Por ID.
    * Por departamento.
    * Por puesto.
    * Por apellido.
    * Por prefijo del apellido.
    * Por coincidencia parcial del apellido.
* Paginación y ordenamiento mediante `Pageable`.
* Validación de datos con Jakarta Validation.
* Manejo global de excepciones mediante `@RestControllerAdvice`.
* Respuestas de error unificadas utilizando `ErrorResponseDTO`.
* Documentación interactiva con Swagger/OpenAPI.
* Arquitectura en capas (Controller → Service → Repository).

---

## 🛠 Tecnologías

* Java 21
* Spring Boot 4
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Jakarta Validation
* Springdoc OpenAPI (Swagger)
* Git
* GitHub

---

## 🏗 Arquitectura

```text
Cliente (Postman / Swagger)
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
PostgreSQL
```

Cada capa posee una responsabilidad específica:

* **Controller:** recibe las peticiones HTTP y devuelve las respuestas.
* **Service:** contiene la lógica de negocio.
* **Repository:** interactúa con la base de datos mediante Spring Data JPA.
* **PostgreSQL:** almacena la información persistente.

---

## 📂 Funcionalidades

### Empleados

| Método | Endpoint                              | Descripción                                   |
| ------ | ------------------------------------- | --------------------------------------------- |
| GET    | `/api/v1/empleados`                   | Obtener todos los empleados (paginado).       |
| GET    | `/api/v1/empleados/{id}`              | Obtener empleado por ID.                      |
| POST   | `/api/v1/empleados`                   | Crear un empleado.                            |
| PUT    | `/api/v1/empleados/{id}`              | Actualizar un empleado.                       |
| DELETE | `/api/v1/empleados/{id}`              | Eliminar un empleado.                         |
| GET    | `/api/v1/empleados/departamento`      | Buscar empleados por departamento.            |
| GET    | `/api/v1/empleados/puesto`            | Buscar empleados por puesto.                  |
| GET    | `/api/v1/empleados/apellido`          | Buscar empleados por apellido.                |
| GET    | `/api/v1/empleados/apellido/prefijo`  | Buscar por prefijo del apellido.              |
| GET    | `/api/v1/empleados/apellido/contiene` | Buscar por coincidencia parcial del apellido. |

### Departamentos

| Método | Endpoint                     | Descripción                      |
| ------ | ---------------------------- | -------------------------------- |
| GET    | `/api/v1/departamentos`      | Obtener todos los departamentos. |
| GET    | `/api/v1/departamentos/{id}` | Obtener departamento por ID.     |
| POST   | `/api/v1/departamentos`      | Crear un departamento.           |
| PUT    | `/api/v1/departamentos/{id}` | Actualizar un departamento.      |
| DELETE | `/api/v1/departamentos/{id}` | Eliminar un departamento.        |

---

## 📄 Paginación

Los listados de empleados admiten paginación y ordenamiento.

Ejemplo:

```http
GET /api/v1/empleados?page=0&size=5&sort=apellido,asc
```

También es posible ordenar por múltiples campos:

```http
GET /api/v1/empleados?page=0&size=5&sort=apellido,desc&sort=nombre,asc
```

---

## ⚠ Manejo de errores

La API devuelve respuestas de error con un formato uniforme.

Ejemplo:

```json
{
  "status": 404,
  "message": "No existe el empleado con id: 10",
  "timestamp": "2026-08-04T00:00:00",
  "path": "/api/v1/empleados/10"
}
```

Para errores de validación también se informa el detalle de cada campo.

```json
{
  "status": 400,
  "message": "Error de validación en los campos enviados",
  "details": {
    "nombre": "El nombre es obligatorio",
    "puesto": "El puesto debe tener entre 3 y 30 caracteres"
  }
}
```

---

## 📚 Documentación

Una vez iniciada la aplicación, Swagger se encuentra disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

Desde allí pueden probarse todos los endpoints de forma interactiva.

---

## ⚙ Configuración

El proyecto utiliza PostgreSQL y variables de entorno para proteger las credenciales de acceso.

### Variables de entorno

Configurar previamente:

```text
DB_USER=postgres
DB_PASSWORD=tu_contraseña
```

### Ejemplo de `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestion_empleados
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Hibernate creará o actualizará automáticamente las tablas necesarias al iniciar la aplicación.

---

## ▶ Ejecutar el proyecto

1. Clonar el repositorio.

```bash
git clone https://github.com/quirogafacundo97/empleados-backend.git
```

2. Crear una base de datos PostgreSQL llamada:

```sql
CREATE DATABASE gestion_empleados;
```

3. Configurar las variables de entorno:

```text
DB_USER=postgres
DB_PASSWORD=tu_contraseña
```

4. Ejecutar la aplicación.

**En Windows:**

```bash
mvnw.cmd spring-boot:run
```

**En Linux/macOS:**

```bash
./mvnw spring-boot:run
```

> También es posible ejecutar la aplicación con `mvn spring-boot:run` si Maven está instalado en el sistema.

5. Abrir la documentación Swagger en el navegador:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🔮 Próximas mejoras

- Incorporar MapStruct para el mapeo entre entidades y DTOs.
- Implementar autenticación con Spring Security + JWT.
- Agregar pruebas unitarias e integración.
- Dockerizar la aplicación.

---

## 👨‍💻 Autor

**Juan Facundo Quiroga**

- GitHub: https://github.com/quirogafacundo97
- LinkedIn: https://www.linkedin.com/in/juan-facundo-quiroga-7522aa3b3/