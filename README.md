# Alumware

Alumware es una plataforma académica integral desarrollada en Java con Spring Boot, orientada a la gestión y mejora de la experiencia universitaria. Permite a estudiantes, docentes y personal administrativo interactuar en torno a trámites, cursos, docentes, reseñas, reacciones, favoritos y más, fomentando la transparencia, la colaboración y la mejora continua en el entorno educativo.

## Características principales
- **Gestión de usuarios**: Registro, autenticación y asignación de roles (ADMIN, MODERATOR, STUDENT, WRITER).
- **Trámites universitarios**: Solicitud, seguimiento y comentarios sobre formalidades (trámites) como carnet universitario, becas, constancias, etc.
- **Gestión de docentes y cursos**: Información detallada de profesores, cursos, carreras, campus y formatos.
- **Reseñas y reacciones**: Los estudiantes pueden dejar reseñas a los docentes y reaccionar a ellas.
- **Favoritos**: Permite marcar docentes, cursos o trámites como favoritos para acceso rápido.
- **Etiquetas y búsqueda**: Uso de tags para categorizar y buscar fácilmente información relevante.
- **Panel de administración**: Funcionalidades avanzadas para usuarios con roles de ADMIN y MODERATOR.

## Estructura del proyecto
- **src/main/java/com/upc/cicloestrella/entities**: Entidades JPA del dominio.
- **src/main/java/com/upc/cicloestrella/controllers**: Controladores REST para cada recurso.
- **src/main/java/com/upc/cicloestrella/services**: Lógica de negocio y servicios de autorización.
- **src/main/java/com/upc/cicloestrella/DTOs**: Objetos de transferencia de datos para requests y responses.
- **src/main/java/com/upc/cicloestrella/repositories**: Repositorios Spring Data JPA.
- **src/main/resources/db/import.sql**: Script de inicialización de la base de datos con datos de prueba.

## Roles y permisos
- **ADMIN**: Acceso total a todas las funcionalidades, gestión de usuarios, roles y recursos.
- **MODERATOR**: Acceso a moderación de contenido, gestión de reseñas y comentarios.
- **STUDENT**: Acceso a trámites, creación de reseñas, comentarios y favoritos.
- **WRITER**: Puede crear contenido específico según la configuración de la plataforma.

## Acceso a la documentación Swagger
Una vez levantada la aplicación (por defecto en `http://localhost:8080`), puedes acceder a la documentación interactiva de la API en:

```
http://localhost:8080/swagger-ui/index.html
```

Desde ahí podrás probar todos los endpoints, ver los modelos de datos y las respuestas esperadas según el rol autenticado.

## Ejecución y pruebas
1. Clona el repositorio y navega a la carpeta del proyecto.
2. Asegúrate de tener Java 17+ y PostgreSQL instalados.
3. Configura la conexión a la base de datos en `src/main/resources/application.yml`.
4. Ejecuta el proyecto con:
   ```
   ./mvnw spring-boot:run
   ```
5. La base de datos se inicializará automáticamente con los datos de `import.sql`.

## Usuarios de prueba
En el archivo `import.sql` encontrarás usuarios de ejemplo con los siguientes roles:

| Usuario   | Email               | Rol principal         |
|-----------|---------------------|----------------------|
| alicia    | alicia@email.com    | ADMIN                |
| roberto   | roberto@email.com   | MODERATOR            |
| carlos    | carlos@email.com    | STUDENT              |
| diana     | diana@email.com     | STUDENT              |
| eva       | eva@email.com       | ADMIN, MODERATOR     |
| francisco | francisco@email.com | STUDENT              |
| graciela  | graciela@email.com  | STUDENT              |
| hector    | hector@email.com    | STUDENT              |
| ines      | ines@email.com      | STUDENT              |
| juan      | juan@email.com      | STUDENT              |

## Contribución
Las contribuciones son bienvenidas. Por favor, abre un issue o pull request para sugerencias o mejoras.

---

**Alumware** — Plataforma académica para la comunidad universitaria.
