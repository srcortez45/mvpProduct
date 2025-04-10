# 🛠️ MVP API - Documentación

---

## 📦 Requisitos

- Java 21+
- Spring Boot 3.x
- SQL Server 2019
- Maven
- Insomnia o Postman (para probar la API)

---

## 💄 Configuración de Base de Datos

### Crear la base de datos

```sql
CREATE DATABASE mvp_database;
GO
USE mvp_database;
GO
```

### Crear las tablas

```sql
CREATE TABLE users (
    id INT PRIMARY KEY IDENTITY(1,1),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    creation_date DATETIME,
    last_login DATETIME,
    access_level SMALLINT,
    user_state INT
);

CREATE TABLE product_category (
    id INT PRIMARY KEY IDENTITY(1,1),
    category_name VARCHAR(255),
    creation_date DATETIME,
    last_upt_date DATETIME,
    category_state INT
);

CREATE TABLE tags (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255),
    tag_state INT
);

CREATE TABLE products (
    id INT PRIMARY KEY IDENTITY(1,1),
    product_name VARCHAR(255),
    category_id INT,
    price BIGINT,
    creation_date DATETIME,
    last_upt_date DATETIME,
    product_state INT,
    FOREIGN KEY (category_id) REFERENCES product_category(id)
);

CREATE TABLE product_tags (
    product_id INT,
    tag_id INT,
    PRIMARY KEY (product_id, tag_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);
```

---

## 🔐 Autenticación

### Generar Token

`POST /api/v1/auth/generateToken`

```json
{
  "username": "jaycor1",
  "password": "1234"
}
```

---

## 👤 Usuarios

- `GET /api/v1/users`
- `GET /api/v1/users/{id}`
- `POST /api/v1/users` (requiere rol ADMIN)

```json
{
  "firstName": "Jose",
  "lastName": "Cortez",
  "username": "jaycor3",
  "password": "1234"
}
```

- `PUT /api/v1/users` (actualiza usuario)
- `PUT /api/v1/users/{id}` (cambia el estado del usuario)

---

## 🏢 Productos

- `GET /api/v1/products`
- `GET /api/v1/products/{id}`
- `POST /api/v1/products`

```json
{
  "productName": "Headphones",
  "categoryId": 1,
  "price": 25000,
  "tagIds": [1, 3]
}
```

- `PUT /api/v1/products` (actualiza precio y etiquetas)
- `PUT /api/v1/products/{id}` (cambia el estado del producto)

---

## 📂 Categorías

- `GET /api/v1/products/categories`
- `GET /api/v1/products/categories/{id}`
- `POST /api/v1/products/categories`

```json
{
  "categoryName": "Electronics"
}
```

- `PUT /api/v1/products/categories`
- `PUT /api/v1/products/categories/{id}` (cambia el estado)

---

## 🏷️ Etiquetas

- `GET /api/v1/products/tags`
- `GET /api/v1/products/tags/{id}`
- `POST /api/v1/products/tags`

```json
{
  "name": "Gaming"
}
```

- `PUT /api/v1/products/tags` (actualiza nombre)
- `PUT /api/v1/products/tags/{id}/disable` (cambia estado)

---
