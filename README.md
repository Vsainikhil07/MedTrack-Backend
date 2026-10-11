# MedTrack – Backend

## About the Project

MedTrack Backend is a server-side application developed using Java and Spring Boot. It handles application requests, implements business logic, and manages medicine records using a MySQL database.

## Features

- REST API development using Spring Boot
- Medicine CRUD operations: Create, Read, Update, and Delete
- User-specific medicine record retrieval
- Database interaction using Spring Data JPA
- Persistent data storage with MySQL
- Maven-based dependency and build management

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Project Structure

```text
backend/
├── .mvn/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Prerequisites

- Java JDK
- MySQL Server
- Maven (or the included Maven Wrapper)

## How to Run

1. Configure your local MySQL connection in `application.properties`.
2. Open a terminal in the backend directory.
3. Run the application on Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. The Spring Boot application starts on the configured port.

## Database

MySQL stores the application's data, while Spring Data JPA and Hibernate support database operations.

## Security Note

Database credentials and other sensitive configuration values should remain private and must not be committed to the repository.

---

**MedTrack Backend | Java · Spring Boot · MySQL**

## Author

**Vadla Sai Nikhil**  
Anurag University 
Roll No : 25EG504D07
B.Tech – ECE-D, 3rd Year
