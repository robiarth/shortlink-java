# ShortURL Project – Java Spring Boot REST API

## 📌 Project Summary

This project is a simple REST API to shorten and manage URLs, built with Spring Boot and deployed on Google App Engine. It provides endpoints to create, read, update, delete, and redirect short URLs.

## 🧱 Technologies & Architecture

- **Java 21**
- **Spring Boot 3.5**
  - Spring Web
  - Spring Data JPA
- **MySQL** hosted on **Google Cloud SQL**
- **Swagger UI** for API documentation (`springdoc-openapi`)
- **Maven** for dependency management
- **Deployed** using **Google App Engine Standard Environment**
- **Security**: Basic API Key check via HTTP header

## 🔐 Security

- API key is required for sensitive operations (`PUT`, `DELETE`)
- Provided as `X-API-KEY` heade

## 🔗 API Endpoints

- [Swagger](https://local-pointer-464614-m4.wl.r.appspot.com/swagger-ui/index.html#/) - routes listing

## 🗂️ Project Structure

```
src/
└── main/
    ├── java/
    │   └── com/shorturl/shorturl/
    │       ├── controller/
    │       ├── model/
    │       ├── repository/
    │       ├── service/
    │       └── ShortUrlApplication.java
    └── resources/
        └── application.properties
```

## 🚀 Deployment

The application is packaged using **Maven Wrapper**:

```bash
./mvnw clean package
```

Then deployed on **Google App Engine** with:

```bash
gcloud app deploy
```

## 📚 Resources Used

- [YouTube](https://www.youtube.com](https://www.youtube.com/watch?v=Ey554n5odLk)) – tutorial for CRUD in Java Spring Boot and MySQL
- [Spring Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/) – official reference for Spring Boot and related frameworks
- [ChatGPT](https://chat.openai.com) – For troubleshooting, documentation, and step by step guidance for deployment
