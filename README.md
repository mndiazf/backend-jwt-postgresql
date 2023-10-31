# Backend JWT PostgreSQL 🌱

Aplicación robusta basada en **Spring Boot v3.1.5** que proporciona autenticación y registro de usuarios a través de JWT (JSON Web Tokens).

## Descripción 🔍

Esta aplicación se diseñó para ofrecer una interfaz API segura y eficiente para la autenticación y registro de usuarios. Utilizando JWT, garantizamos que cada usuario reciba un token único tras la autenticación, proporcionando así una capa adicional de seguridad.

## 🚀 Tecnologías y Dependencias

### Tecnologías principales

- **JDK 17**
- **Spring Boot**: v3.1.5
- **Base de datos**: PostgreSQL v16
- **Administrador de base de datos**: pgAdmin (versión: latest)

### Dependencias de Maven

```xml
<!-- Spring Security para la autenticación -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- DevTools para desarrollo -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>

<!-- Lombok para reducir boilerplate code -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Web starter para el desarrollo web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- JPA para la persistencia de datos -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Docker Compose support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>

<!-- Driver para PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Dependencias relacionadas con JWT -->

<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

## Funcionalidades 📐

### Registro

**Endpoint**: `/register`  
**Método**: `POST`

JSON esperado:

```json
{
    "username": "nombreDeUsuario",
    "password": "contraseña",
    "firstname": "nombre",
    "lastname": "apellido",
    "country": "pais"
}
```

Respuesta tras un registro exitoso:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW1lbiIsImlhdCI6MTY5ODY3NTA1OSwiZXhwIjoxNjk4Njc2NDk5fQ.ER4q6W4JCTEnjraV5YeXnVChioBy3x0CjzrlFyIx9Ds"
}
```

### Login

**Endpoint**: `/login`  
**Método**: `POST`

JSON esperado:


```json
{
    "username": "nombreDeUsuario",
    "password": "contraseña"
}
```

Respuesta tras un login exitoso:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW1lbiIsImlhdCI6MTY5ODY3NTA1OSwiZXhwIjoxNjk4Njc2NDk5fQ.ER4q6W4JCTEnjraV5YeXnVChioBy3x0CjzrlFyIx9Ds"
}
```

# Instalación y Uso ⚙️

**Clonar el repositorio:**

```bash
git clone https://github.com/mndiazf/backend-spring-boot-jwt-postgresql.git
```

**Navegar al directorio del proyecto:**

```bash
cd backend-spring-boot-jwt-postgresql
```

**Instalar las dependencias:**

```bash
mvn install
```

**Ejecutar la aplicación:**

```bash
mvn spring-boot:run
```

**Acceder a la aplicación:**
Abre tu navegador y dirígete a `http://localhost:8080`.

# Pruebas con Postman 👨‍🚀

Para probar los endpoints de la API con Postman:

**Abre Postman.**

1. Configura el método deseado (por ejemplo, POST).
2. Ingresa la URL del endpoint, por ejemplo http://localhost:8080/auth/register o http://localhost:8080/auth/login.
3. En la pestaña Body, elige raw y selecciona formato JSON (application/json).
4. Ingresa el payload correspondiente.
5. Haz clic en Send.
