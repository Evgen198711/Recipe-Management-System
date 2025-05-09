# Recipe Management System

## Overview

The **Recipe Management System** is a RESTful web application developed using Java and Spring Boot. It facilitates the management of recipes, allowing users to perform CRUD (Create, Read, Update, Delete) operations. The application incorporates Spring Security for authentication and authorization, ensuring secure access to its endpoints.

## Features

- User Authentication and Authorization
- Recipe CRUD Operations
- Layered Architecture
- Robust Exception Handling

## Technologies Used

- Java
- Spring Boot
- Spring Security
- RESTful APIs

## Project Structure

- `adapters`: Interfaces and connectors
- `business`: Business logic and services
- `config`: Security and application configurations
- `exceptions`: Custom exception handling
- `persistence`: Data access layer
- `pojo`: Data transfer objects
- `presentation`: REST controllers

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven or Gradle

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Evgen198711/Recipe-Management-System.git
   cd Recipe-Management-System
   ```

2. Build the project:

   **Using Maven:**
   ```bash
   mvn clean install
   ```

   **Using Gradle:**
   ```bash
   ./gradlew build
   ```

3. Run the application:

   **Using Maven:**
   ```bash
   mvn spring-boot:run
   ```

   **Using Gradle:**
   ```bash
   ./gradlew bootRun
   ```

4. Access the application at:
   ```
   http://localhost:8080
   ```

## API Endpoints

- `POST /api/recipes` - Create a recipe
- `GET /api/recipes` - List all recipes
- `GET /api/recipes/{id}` - Get recipe by ID
- `PUT /api/recipes/{id}` - Update recipe
- `DELETE /api/recipes/{id}` - Delete recipe

*Authentication required.*

## Security

Spring Security provides user authentication and role-based access control.

## License

MIT License. See [LICENSE](LICENSE) for details.

## Acknowledgments

Developed by [Evgen198711](https://github.com/Evgen198711).