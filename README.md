# News API с Spring Security & MapStruct

## Описание

Приложение для управления новостями с **полным CRUD**, **Spring Security** (3 роли), **MapStruct** и сложными связями между сущностями.

В системе есть 4 сущности:
- **User** — пользователь;
- **Category** — категория новости;
- **News** — новость;
- **Comment** — комментарий.

Связи:
- User 1:M News 1:M Comment
- News M:1 Category
- Comment M:1 News M:1 User

---

## Что умеет приложение

- **полный CRUD** для всех 4 сущностей;
- **роли USER/ADMIN/MODERATOR** с точными правами из SecurityConfig;
- **AOP** `@AuthorOnly` для News/Comment, `@UserPermission` для User;
- **специальные методы** поиска комментариев по новости;
- **автоматический маппинг** через MapStruct.

---

## Безопасность (SecurityConfig)

### HTTP Basic + Stateless
- `.sessionCreationPolicy(SessionCreationPolicy.STATELESS)`
- `.httpBasic(Customizer.withDefaults())`

### Точные права доступа:

| Эндпоинт | Метод | Роли |
|----------|-------|------|
| `/news-service/user` | **GET** | **ADMIN** |
| `/news-service/user/**` | **GET/PUT/DELETE** | **USER/ADMIN/MOD** |
| `/news-service/user` | **POST** | **permitAll** |
| `/news-service/category/**` | **GET** | **USER/ADMIN/MOD** |
| `/news-service/category/**` | **POST/PUT/DELETE** | **ADMIN/MOD** |
| `/news-service/news/**` | **ВСЕ** | **USER/ADMIN/MOD** |
| `/news-service/comment/**` | **ВСЕ** | **USER/ADMIN/MOD** |

**Дополнительно AOP**:
- `@AuthorOnly` → редактирование своих News/Comment
- `@UserPermission` → редактирование своего профиля

---

## Эндпоинты

### Users `/news-service/user`
```http
GET    /news-service/user           # ADMIN только!
GET    /news-service/user/{id}      # USER/ADMIN/MOD + @UserPermission
POST   /news-service/user           # permitAll
PUT    /news-service/user/{id}      # USER/ADMIN/MOD + @UserPermission
DELETE /news-service/user/{id}      # USER/ADMIN/MOD + @UserPermission
```

### Categories `/news-service/category`
```http
GET    /news-service/category
GET    /news-service/category/{id}
POST   /news-service/category       # ADMIN/MOD
PUT    /news-service/category/{id}  # ADMIN/MOD
DELETE /news-service/category/{id}  # ADMIN/MOD
```

### News `/news-service/news`
```http
GET    /news-service/news
GET    /news-service/news/{id}
POST   /news-service/news
PUT    /news-service/news/{id}      # USER/ADMIN/MOD + @AuthorOnly
DELETE /news-service/news/{id}      # USER/ADMIN/MOD + @AuthorOnly
```

### Comments `/news-service/comment`
```http
GET    /news-service/comment/to-news/{newsId}  # Комменты к новости
GET    /news-service/comment/{id}
POST   /news-service/comment
PUT    /news-service/comment/{id}     # USER/ADMIN/MOD + @AuthorOnly
DELETE /news-service/comment/{id}     # USER/ADMIN/MOD + @AuthorOnly
```

---

## DTO & MapStruct

**Все контроллеры используют MapStruct**:
- `newsMapper.newsListToNews()` → `NewsListResponse`
- `newsMapper.newsToResponse()` → `NewsResponse`
- `newsMapper.requestToNews()` → `News entity`

**Аналогично** для `User`/`Category`/`Comment`.

---

### 1. Запуск
```bash
docker-compose up --build
```

### 2. Сервер доступен
```text
PostgreSQL: localhost:5432 (newsuser/newspass)
App: localhost:8081
```

---

## Итог

**Docker Compose** с PostgreSQL + App (8081)  
**SecurityConfig** с точными ролями  
**4 контроллера** `/news-service/*`  
**AOP** `@AuthorOnly` / `@UserPermission`  
**MapStruct** маппинг  
**HTTP Basic** аутентификация  
