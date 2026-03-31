# News API: JPA + MapStruct + Spring Security

REST API новостного сервиса с CRUD (User/Category/News/Comment), Security (роли USER/ADMIN/MODERATOR), AOP, Specifications, MapStruct, валидацией.

---

## Стек технологий 

**Spring Boot 4.0.0** (Java 17)  
**spring-boot-starter-data-jpa** + **PostgreSQL**  
**spring-boot-starter-webmvc** + **spring-boot-starter-security**  
**springdoc-openapi-ui 2.8.8** (Swagger)  
**MapStruct 1.5.3** + **Lombok**  
**Validation** + **JsonUnit** (тесты)  

---

## API (Swagger: `/swagger-ui.html`)

**База**: `http://localhost:8081`

### Users
```text
GET /users ROLE_ADMIN
GET /users/{id} ROLE_USER/ADMIN/MODERATOR
POST/PUT/DEL /users/{id} ROLE_USER/ADMIN/MODERATOR
```

### Categories
```text
GET /categories ROLE_USER+
POST/PUT/DEL /categories/{id} ROLE_ADMIN/MODERATOR
```

### News
```text
GET /news ROLE_USER+
GET /news/{id} (с комментариями) ROLE_USER+
POST /news ROLE_USER+
PUT/DEL /news/{id} @AuthorOnly ROLE_USER+
```

### Comments
```text
GET /news/{id}/comments ROLE_USER+
POST /news/{id}/comments ROLE_USER+
PUT/DEL /comments/{id} @AuthorOnly ROLE_USER+
```

---

## Ключевые фичи

### 1. **Spring Security** (pom.xml)
spring-boot-starter-security

text
- JWT аутентификация
- Роли: `ROLE_USER/ADMIN/MODERATOR`
- `@PreAuthorize("hasRole('ADMIN')")`

### 2. **MapStruct** (1.5.3 + processor)
NewsMapper: Entity ↔ DTO
NewsDto содержит commentsCount (НЕ List<Comment>)

### 3. **PostgreSQL** + JPA
spring-boot-starter-data-jpa
postgresql (runtime)

### 4. **Swagger** (2.8.8)
http://localhost:8080/swagger-ui.html

---

## Запуск

### Локально
```bash
mvn clean compile  # MapStruct генерирует мапперы
mvn spring-boot:run
```

**Swagger**: `http://localhost:8081/swagger-ui.html`

### PostgreSQL (docker-compose.yml)
```yaml
postgres:
  image: postgres:15
  environment:
    POSTGRES_DB: newsdb
    POSTGRES_USER: user
    POSTGRES_PASSWORD: pass
  ports:
    - "5432:5432"
```

```bash
docker-compose up -d 
mvn spring-boot:run
```

---
