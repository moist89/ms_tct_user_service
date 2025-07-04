# Gestión de Usuarios

##  Descripción

Este servicio permite la **creación, actualización y gestión de usuarios** mediante una API RESTful desarrollada con Java 21 y Spring Boot.

## Repositorio

| Elemento       | Enlace o valor |
|----------------|----------------|
| Repositorio    | [ms_tct_user_service](https://github.com/moist89/ms_tct_user_service.git) |
| Branch actual  | `feature/programacion_imperativa` |
| Swagger UI     | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html#/user-controller/create) |

##  Tecnologías utilizadas

- Java 21
- Spring Boot
- Maven
- H2 Database
- Jakarta JPA
- Jakarta Validation
- JWT (JSON Web Token)
- Docker
- Swagger / OpenAPI

##  Arquitectura del Proyecto

| Carpeta / Archivo                            | Descripción |
|----------------------------------------------|-------------|
| `com/interview/technical/configs`            | Configuraciones generales (por ejemplo, Swagger) |
| `com/interview/technical/controllers`        | Controladores REST |
| `com/interview/technical/dtos/api/request`   | DTOs de entrada |
| `com/interview/technical/dtos/api/response`  | DTOs de salida |
| `com/interview/technical/enums`              | Enumeraciones del dominio |
| `com/interview/technical/exceptions`         | Excepciones personalizadas |
| `com/interview/technical/handlers`           | Manejadores de errores globales (ControllerAdvice) |
| `com/interview/technical/mappers`            | Mapeadores entre entidades y DTOs |
| `com/interview/technical/models`             | Entidades JPA |
| `com/interview/technical/repositories`       | Repositorios JPA |
| `com/interview/technical/services`           | Lógica de negocio del sistema |
| `com/interview/technical/utils`              | Funciones auxiliares y utilitarias |
| `com/interview/technical/validators`         | Validaciones personalizadas |
| `resources/scripts/schema.sql`               | Script para la creación de tablas |
| `resources/scripts/data.sql`                 | Script para insertar datos de prueba |
| `resources/docs`                             | Documentación adicional (curls, collections, etc.) |
| `application.properties`                     | Configuración de la aplicación |
| `Dockerfile`                                 | Construcción de la imagen Docker |
| `pom.xml`                                    | Archivo de configuración de Maven |

>  En `resources/scripts/schema.sql` y `resources/scripts/data.sql` se encuentran los scripts para la creación de la base de datos y los datos de prueba.

---

##  Ejecución del Proyecto

Puedes ejecutar el servicio de dos maneras: usando **Docker** o desde un **IDE local**.

### 1. Clonar el proyecto

```bash
git clone https://github.com/moist89/ms_tct_user_service.git
cd ms_tct_user_service
git checkout feature/programacion_imperativa
```

### 2. Ejecutar la aplicación

#### Opción A: Docker

```bash
docker build -t ms_tct_user_service .
docker run -p 8080:8080 ms_tct_user_service
```

#### Opción B: IDE / Línea de comandos

```bash
mvn spring-boot:run
```

---

##  Pruebas y documentación

### 3.1 Swagger UI

Explora y prueba los endpoints disponibles en:

 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html#/user-controller/create)

### 3.2 Curl de prueba

Ubicado en:

```
./resources/docs/curls.txt
```

### 3.3 Collection para Insomnia

Importa el archivo:

```
./resources/docs/collection_insomnia.json
```

en la aplicación [Insomnia](https://insomnia.rest/) para pruebas automatizadas de los endpoints.

---

##  Notas

- Se incluye configuración para base de datos H2 en memoria por defecto (`application.properties`).
- Los scripts SQL se ejecutan automáticamente al iniciar la aplicación.

---
