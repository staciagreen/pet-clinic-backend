# Pet Clinic Backend

REST API для управления ветеринарной клиникой с аутентификацией и управлением доступом на основе ролей.

## Обзор

Этот проект реализует полнофункциональную систему управления ветеринарной клиникой, охватывающую:
- Java 17 с Spring Boot 3
- JPA/Hibernate с PostgreSQL
- Spring Security с HTTP Basic аутентификацией
- REST API с использованием DTO и пагинации
- Полный набор тестов с использованием H2 в памяти

## Стек технологий

- Java 17 (LTS)
- Spring Boot 3.2.5
  - Spring Web
  - Spring Data JPA
  - Spring Security 6
- PostgreSQL (production)
- H2 (тестирование в памяти)
- Hibernate ORM
- Maven 3.9.6
- Swagger/OpenAPI 3

## Архитектура

Многоуровневая архитектура в соответствии со стандартами Spring:

```
Слой контроллеров (REST endpoints)
    ||
    v
Слой сервисов (бизнес-логика)
    ||
    v
Слой репозиториев (доступ к данным через Spring Data JPA)
    ||
    v
База данных
```

## Модель безопасности

- Аутентификация: HTTP Basic Auth с пользователем и паролем
- Авторизация: Управление доступом на основе ролей (ADMIN и USER)
- Хранение пароля: Шифрование BCrypt
- Защищенные endpoints: Все endpoints кроме /api/auth/register, /api/auth/login, /api/auth/me и Swagger UI

## Как запустить

### Production профиль (PostgreSQL)

#### Предварительные требования
- PostgreSQL 15+ установлен и работает
- Java 17+

#### Установка

1. Создайте базу данных
```bash
createdb lab2_db
```

2. Установите переменные окружения
```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/lab2_db
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=your_password
export JWT_SECRET=your-strong-secret-key-min-32-characters
export CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080
```

3. Соберите и запустите
```bash
./mvnw clean install
./mvnw spring-boot:run
```

Сервер запустится на **http://localhost:8080**

Swagger UI: **http://localhost:8080/swagger-ui/index.html**

### Test профиль (H2 в памяти)

Тесты запускаются с использованием H2 в памяти, PostgreSQL не требуется.

```bash
./mvnw clean test
```

Конфигурация тестов в src/test/resources/application-test.properties:
- База данных: H2 в памяти с режимом совместимости PostgreSQL
- DDL: create-drop (новая БД для каждого запуска тестов)
- Flyway: отключен
- JWT Secret: безопасное значение для тестов

## Примеры API

### Регистрация пользователя

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123",
    "name": "John Doe",
    "birthDate": "1990-05-15"
  }'
```

Ответ: 201 Created

### Вход в систему

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123"
  }'
```

Ответ: 200 OK с данными пользователя

### Доступ к защищенному endpoint (с HTTP Basic Auth)

```bash
curl -X GET http://localhost:8080/api/owners \
  -u john_doe:SecurePass123
```

Ответ: 200 OK (если пользователь имеет роль ADMIN)

### Получить питомцев с пагинацией

```bash
curl -X GET "http://localhost:8080/api/pets/paged?page=0&size=10" \
  -u john_doe:SecurePass123
```

## Тестирование

### Запуск всех тестов
```bash
./mvnw clean test
```

### Запуск конкретного теста
```bash
./mvnw test -Dtest=AuthControllerTest
```

### Покрытие тестами
- Интеграционные тесты для всех контроллеров с использованием MockMvc
- Тесты используют @ActiveProfiles("test") для H2 в памяти
- Нет необходимости в внешних зависимостях (PostgreSQL, внешние сервисы)
- Тесты проверяют аутентификацию, авторизацию и HTTP коды статуса

## Структура проекта

```
src/
├── main/
│   ├── java/PetBase/
│   │   ├── Application.java              # Точка входа Spring Boot
│   │   ├── config/
│   │   │   ├── SecurityConfig.java       # Конфигурация безопасности
│   │   │   └── OpenApiConfig.java        # Конфигурация Swagger
│   │   ├── controller/                   # REST контроллеры
│   │   ├── service/                      # Бизнес-логика
│   │   ├── dao/
│   │   │   ├── Repository/               # Spring Data репозитории
│   │   │   └── model/                    # JPA сущности
│   │   ├── exception/                    # Обработка исключений
│   │   └── security/                     # Компоненты безопасности
│   └── resources/
│       ├── application.properties        # Конфигурация production
│       └── hibernate.cfg.xml
└── test/
    ├── java/PetBase/
    │   └── AuthControllerTest.java       # Интеграционные тесты
    └── resources/
        └── application-test.properties   # Конфигурация тестов (H2)
```

## Переменные окружения

Production развертывание требует:
- DATABASE_URL: строка подключения PostgreSQL
- DATABASE_USERNAME: пользователь базы данных
- DATABASE_PASSWORD: пароль базы данных
- JWT_SECRET: сильная случайная строка для подписи JWT (минимум 32 символа)
- CORS_ALLOWED_ORIGINS: список разделенных запятыми разрешенных источников
- JWT_EXPIRATION_MS: время истечения токена в миллисекундах

Пример файла .env находится в .env.example

## Примечания безопасности

- JWT secret должен быть экстернализован и никогда не закодирован в репозитории
- Пароли шифруются с помощью BCrypt
- CORS ограничивается настроенными источниками (без подстановочного символа *)
- Все endpoints кроме публичных endpoints аутентификации требуют аутентификации
- Тесты используют безопасный JWT secret из application-test.properties

## Сборка с Maven Wrapper

```bash
./mvnw clean install      # Собрать проект
./mvnw clean test        # Запустить тесты
./mvnw spring-boot:run   # Запустить приложение
```

Windows:
```bash
mvnw.cmd clean install
```

## Примечания для портфолио

Этот проект демонстрирует:
- Многоуровневую архитектуру (контроллер -> сервис -> репозиторий)
- Чистое разделение ответственности (DTO, сущности, мапперы)
- Полнофункциональную обработку ошибок с RestControllerAdvice
- Конфигурацию Spring Security с управлением доступом на основе ролей
- Best practices REST API (правильные HTTP коды статуса, пагинация)
- Покрытие тестами с MockMvc и H2 в памяти
- Production-ready конфигурацию с переменными окружения
- Профессиональное качество кода (без комментариев, чистые импорты, нет мертвого кода)

