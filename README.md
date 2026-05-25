# News API

REST API новостного портала: пользователи, новости, категории, комментарии.

## Стек технологий

| Слой | Технологии |
|------|-----------|
| Backend | Java 17, Spring Boot 3.x |
| База данных | PostgreSQL, Spring Data JPA |
| Фильтрация | JPA Specifications + Pageable |
| Безопасность | Spring Security |
| AOP | Spring AOP (@AuthorOnly) |
| Прочее | MapStruct, Lombok |

## Как запустить

### 1. Создать БД

```sql
CREATE DATABASE news_api;
```

### 2. Настроить `application.yaml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/news_api
    username: postgres
    password: ваш_пароль
```

### 3. Запустить

```bash
mvn spring-boot:run
```

## API

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| POST | `/api/users` | Регистрация |
| GET | `/api/news?userId=&categoryId=&page=&size=` | Фильтрация новостей |
| POST | `/api/news` | Создать новость |
| PUT | `/api/news/{id}` | Редактировать (только автор) |
| DELETE | `/api/news/{id}` | Удалить (только автор) |
| GET | `/api/categories` | Список категорий |
| POST | `/api/comments` | Добавить комментарий |
| PUT | `/api/comments/{id}` | Редактировать (только автор) |

## Ключевые решения

**AOP @AuthorOnly:** аспект перехватывает запросы к edit/delete-эндпоинтам, извлекает текущего пользователя из SecurityContext и сравнивает с автором ресурса — проверка не дублируется в каждом сервисе.

**JPA Specifications:** динамическая фильтрация новостей по любой комбинации `userId`, `categoryId`, `categoriesIds` без написания отдельных запросов для каждого варианта.

## Запуск тестов

```bash
mvn test
```
