# Mi Aplicación Spring Boot 🌱

Aplicación robusta basada en **Spring Boot v3.1.5** que proporciona autenticación y registro de usuarios a través de JWT (JSON Web Tokens).

## Índice
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Descripción](#descripción)
- [Funcionalidades](#funcionalidades)
- [Instalación y Uso](#instalación-y-uso)
- [Tests](#tests)
- [Licencia](#licencia)
- [Contacto](#contacto)

## Tecnologías Utilizadas 🚀
- **Spring Boot**: v3.1.5
- **Base de datos**: PostgreSQL v16
- **Administrador de base de datos**: pgAdmin (versión: latest)

## Descripción 🔍

Esta aplicación se diseñó para ofrecer una interfaz API segura y eficiente para la autenticación y registro de usuarios. Utilizando JWT, garantizamos que cada usuario reciba un token único tras la autenticación, proporcionando así una capa adicional de seguridad.

## Funcionalidades 📐

### Registro

**Endpoint**: `/register`  
**Método**: `POST`

Payload esperado:

```json
{
    "username": "nombreDeUsuario",
    "password": "contraseña",
    "firstname": "nombre",
    "lastname": "apellido",
    "country": "pais"
}
