# IrisDual 3D — Backend API

> API REST segura para la gestión integral de un negocio de impresión 3D, desarrollada como **Trabajo Práctico Integrador (TPI)** universitario. Implementa autenticación basada en tokens JWT, arquitectura en capas y operaciones CRUD completas sobre entidades de negocio.

---

## 🛠️ Tecnologías y Stack Utilizado

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.2.2 | Framework base de la aplicación |
| Spring Data JPA / Hibernate | (incluido en Boot) | ORM y acceso a base de datos MySQL |
| Spring Security 6 | (incluido en Boot) | Control de acceso y protección de rutas |
| JJWT | 0.11.5 | Generación y validación de tokens JWT |
| JUnit 5 | (incluido en Boot) | Pruebas unitarias |
| Maven | 3.x | Gestión de dependencias y ciclo de build |
| MySQL | 8.x | Base de datos relacional |
| Git & GitHub | — | Control de versiones con estrategia de **feature branching** |

---

## 🏗️ Arquitectura del Proyecto

El proyecto implementa una **arquitectura en capas** estándar de Spring Boot:

```
src/main/java/com/irisdual/pedidos_api/
│
├── controller/       → Endpoints REST (públicos y protegidos con JWT)
├── service/          → Lógica de negocio e interfaces de servicio
├── dto/              → Data Transfer Objects para desacoplar la capa de presentación
│   └── auth/         → DTOs específicos para autenticación (registro y login)
├── dto/mapper/       → Mappers de entidad a DTO con MapStruct
├── repository/       → Interfaces Spring Data JPA (acceso a datos)
├── entity/           → Modelos de base de datos
└── security/         → Configuración JWT, filtros y beans de seguridad
```

### Detalle por capa

- **`controller`** — Expone los endpoints REST. Las rutas `/api/auth/**` son públicas; el resto requiere token JWT válido en el header `Authorization: Bearer <token>`.

- **`service` / `dto`** — Contiene la lógica de negocio. Los servicios operan sobre entidades y devuelven DTOs, manteniendo la separación entre la capa de datos y la de presentación.

- **`repository`** — Interfaces que extienden `JpaRepository`. Spring Data genera automáticamente las queries a partir de nombres de métodos (ej: `findUsuarioByEmail`).

- **`entity`** — Modelos JPA: `Pedido`, `Categoria`, `Pieza`, `Usuario` y `Rol`. **No se utiliza Lombok** en esta capa — todos los getters y setters son manuales, garantizando compatibilidad total con Spring Tool Suite (STS) sin necesidad de agentes de IDE.

- **`security`** — Implementación del flujo JWT:
  - `JwtService` — Genera y valida tokens (expiración 24 h, algoritmo HS256).
  - `JwtFilter` — Intercepta cada petición y autentica al usuario desde el `SecurityContext`.
  - `AppConfig` — Define los beans `UserDetailsService`, `BCryptPasswordEncoder`, `AuthenticationProvider` y `AuthenticationManager`.
  - `SecurityConfig` — Configura las reglas de acceso con sintaxis **lambda moderna** de Spring Security 6 (STATELESS, sin sesiones HTTP).

---

## 📋 Historias de Usuario (TPI)

| Historia | Módulo | Descripción |
|---|---|---|
| **HU001** | Gestión base | Registro, consultas y persistencia de `Categoria` y `Pieza` |
| **HU002** | Relaciones | Creación de `Pedido` con relación `@ManyToMany` a `Pieza` |
| **HU003** | CRUD completo | Actualización y eliminación de entidades; manejo de excepciones; pruebas unitarias con JUnit 5 |
| **HU004** | Seguridad | Autenticación JWT, control de acceso por roles (`Usuario` + `Rol`) |

---

## ⚙️ Instalación y Configuración

### Prerrequisitos
- Java 17 instalado y configurado en el `PATH`
- MySQL 8.x corriendo localmente
- Maven 3.x (o usar el wrapper incluido `./mvnw`)

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/EnderJack379/irisdual3d-pedidos-api.git
cd irisdual3d-pedidos-api
```

**2. Crear la base de datos en MySQL**
```sql
CREATE DATABASE irisdual_db;
```

**3. Configurar la conexión en `application.properties`**

Editá el archivo `src/main/resources/application.properties` con tus credenciales:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/irisdual_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=true
```

> **Nota:** `ddl-auto=update` crea automáticamente las tablas al iniciar la aplicación.

**4. Compilar e instalar dependencias**
```bash
./mvnw clean install
```

**5. Levantar la aplicación**

Ejecutá la clase principal:
```
src/main/java/com/irisdual/pedidos_api/PedidosApiApplication.java
```
O desde la terminal:
```bash
./mvnw spring-boot:run
```

La API quedará disponible en: `http://localhost:8080`

---

## 📡 Endpoints Principales

### 🔓 Autenticación (públicos — no requieren token)

| Método | Ruta | Descripción | Body (JSON) |
|---|---|---|---|
| `POST` | `/api/auth/register` | Registra un nuevo usuario | `{ "email": "...", "password": "..." }` |
| `POST` | `/api/auth/authenticate` | Inicia sesión y devuelve el token JWT | `{ "email": "...", "password": "..." }` |

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 🔒 Recursos protegidos (requieren `Authorization: Bearer <token>`)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/categorias` | Lista todas las categorías |
| `POST` | `/api/categorias` | Crea una nueva categoría |
| `GET` | `/api/piezas` | Lista todas las piezas |
| `GET` | `/api/piezas/categoria/{id}` | Lista piezas por categoría |
| `POST` | `/api/piezas` | Crea una nueva pieza |
| `POST` | `/api/piezas/batch` | Crea múltiples piezas |
| `PUT` | `/api/piezas/{id}` | Actualiza una pieza existente |
| `DELETE` | `/api/piezas/{id}` | Elimina una pieza |
| `GET` | `/api/pedidos` | Lista todos los pedidos |
| `GET` | `/api/pedidos/{id}` | Obtiene un pedido por ID |
| `POST` | `/api/pedidos` | Crea un nuevo pedido |

> Para consumir los endpoints protegidos, incluí el token en el header de cada petición:
> ```
> Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
> ```

---

## 📁 Estrategia de Versionado

El proyecto utiliza **feature branching**:
- `main` — Versión estable y entregable
- `feature/hu001`, `feature/hu002`, etc. — Ramas por Historia de Usuario

---

## 👤 Autor

Desarrollado por **IrisDual 3D** — Trabajo Práctico Integrador Universitario.
