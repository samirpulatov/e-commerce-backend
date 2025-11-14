# E-Commerce Backend

A modular, scalable, and secure backend for an e-commerce application built with **Spring Boot**, featuring JWT authentication, role-based authorization, cart management, product APIs, and full Docker support.

## 🚀 Features

### **Authentication & Authorization**

* JWT-based authentication (access & refresh tokens)
* Role-based authorization (USER / ADMIN)
* Secure login and token refresh flow
* Centralized exception handling

### **User Management**

* User registration & login
* Assigning roles to users
* Secure password hashing (BCrypt)

### **Product Management**

* Create, update, delete products (admin)
* View product list & product details (public)

### **Cart Management**

* Add items to cart
* Update item quantity
* Remove items from cart
* Clear the entire cart
* Retrieve user cart

### **Infrastructure**

* Fully containerized using Docker
* Separate containers for backend and database
* Swagger UI for API documentation

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot 3+**
* Spring Web
* Spring Security
* Spring Data JPA
* PostgreSQL / MySQL (configurable)
* JWT (jjwt)
* Springdoc OpenAPI (Swagger)
* Docker & Docker Compose

---

## 🔐 Authentication Flow

### **1. Login**

`POST /auth/login`

* User provides email & password
* Server returns **access token** and **refresh token**

### **2. Access Protected Endpoints**

Send the access token:

```
Authorization: Bearer <token>
```

### **3. Refresh Token**

`POST /auth/refresh`

* Uses the refresh token
* Returns a new access token

---

## 📦 Cart API Overview

### **Add product to cart**

`POST /cart`

### **Update quantity**

`PUT /cart/{productId}`

### **View cart**

`GET /cart`

### **Remove product**

`DELETE /cart/{productId}`

### **Clear cart**

`DELETE /cart`

---

## 🧪 Swagger API Docs

Navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🐳 Running with Docker

### **Build & run the app**

```
docker-compose up --build
```

### **Shut down containers**

```
docker-compose down
```

---

## 📂 Project Structure

```
src/main/java/com/codewithmosh/store/
│
├── config/            # Security configuration
├── controllers/       # REST controllers
├── dtos/              # Request/response DTOs
├── entities/          # JPA entities
├── repositories/      # Spring Data repositories
├── services/          # Business logic
└── filters/           # Security & logging filters
```

---

## 🔧 Environment Variables

Create an `.env` file or export variables:

```
DB_USER=
DB_PASSWORD=
DB_NAME=
JWT_SECRET=
```

---


