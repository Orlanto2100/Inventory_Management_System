# Inventory System

A full-stack inventory management system built as a **modular monolith**, with a Java/Spring Boot backend and a React frontend.

The system manages products, warehouses, storage locations, customers, vendors, inventory, stock movements, purchase orders, and sales orders.

## Tech Stack

| Technology      | Purpose                                 |
| --------------- | --------------------------------------- |
| Java 21         | Backend                                 |
| Spring Boot     | Backend framework                       |
| Spring Data JPA | Data persistence                        |
| Spring Security | Authentication and security             |
| PostgreSQL      | Database                                |
| Maven           | Backend build and dependency management |
| React           | Frontend                                |
| TypeScript      | Frontend development                    |
| Vite            | Frontend build tool                     |

## Architecture

The application follows a **modular monolith** architecture.

Each business domain is organized into its own module within the backend application.

The backend separates responsibilities into controllers, services, repositories, entities, DTOs, and mappers where applicable.

The frontend follows a feature-based structure, with pages organized around the same business domains as the backend.

The backend and frontend are maintained together in a single repository.

## Modules

| Module         | Description                                          |
| -------------- | ---------------------------------------------------- |
| User           | Manages system users                                 |
| Product        | Manages products and product information             |
| Vendor         | Manages product suppliers                            |
| Customer       | Manages customers                                    |
| Warehouse      | Manages warehouses                                   |
| Location       | Manages specific storage locations within warehouses |
| Inventory      | Represents the current stock state                   |
| Stock Movement | Records changes to stock over time                   |
| Purchase Order | Represents orders for incoming products              |
| Sales Order    | Represents orders for outgoing products              |

## Inventory and Stock Movement

The system separates the **current inventory state** from **stock movement history**.

`Inventory` represents the current quantity of a product within a warehouse or location.

`Stock Movement` records changes to inventory over time, such as:

* Incoming stock
* Outgoing stock
* Transfers
* Stock adjustments

This separation allows the system to maintain both the current stock state and a history of changes made to inventory.

## Backend

The backend is built with **Java 21 and Spring Boot**.

It provides REST APIs for the inventory system and handles:

* Business logic
* Data persistence
* Validation
* Authentication and security
* Inventory-related operations
* Order management
* Stock movement tracking

### Backend Architecture

The backend follows a layered structure within each business module:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

DTOs are used for API requests and responses, while mappers handle conversions between DTOs and entities.

## Frontend

The frontend is built with **React, TypeScript, and Vite**.

It provides the user interface for interacting with the inventory system and communicates with the Spring Boot backend through REST APIs.

Frontend features are organized by business domain, including:

* Authentication
* Dashboard
* Users
* Products
* Vendors
* Customers
* Warehouses
* Locations
* Inventory
* Stock Movements
* Purchase Orders
* Sales Orders

## Project Structure

```text
inventory-system/
├── inventory-system-backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── ...
│
└── inventory-system-frontend/
    ├── src/
    │   ├── api/
    │   ├── app/
    │   ├── assets/
    │   └── features/
    ├── public/
    ├── package.json
    └── ...
```

## Getting Started

### Prerequisites

Make sure the following are installed:

* Java 21
* Maven
* PostgreSQL
* Node.js
* npm

### Database Setup

Create a PostgreSQL database named:

```text
inventory_db
```

Configure the database connection in the backend's `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

The application currently uses Hibernate to create the database schema from the JPA entities when the backend starts.

## Running the Application

The backend and frontend run as **two separate development servers** and must both be running for the full application to work.

### 1. Start the Backend

Open a terminal and navigate to the backend directory:

```bash
cd inventory-system-backend
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

### 2. Start the Frontend

Open a **second terminal** and navigate to the frontend directory:

```bash
cd inventory-system-frontend
```

Install the frontend dependencies:

```bash
npm install
```

Start the Vite development server:

```bash
npm run dev
```

The frontend normally runs on:

```text
http://localhost:5173
```

Vite will display the exact URL in the terminal.

### 3. Open the Application

With both servers running, open the frontend in your browser:

```text
http://localhost:5173
```

The frontend communicates with the backend through REST APIs.

```text
┌─────────────────────────┐
│   React + TypeScript    │
│       Frontend          │
│   localhost:5173        │
└────────────┬────────────┘
             │
             │ REST API
             ▼
┌─────────────────────────┐
│      Spring Boot        │
│        Backend          │
│   localhost:8080        │
└────────────┬────────────┘
             │
             │ JPA / Hibernate
             ▼
┌─────────────────────────┐
│       PostgreSQL        │
│      inventory_db       │
└─────────────────────────┘
```

Both servers need to remain running while using the application.

## API Communication

The frontend communicates with the Spring Boot backend through REST APIs.

The frontend API base URL defaults to:

```text
http://localhost:8080/api
```

It is configured in the frontend API client and can also be overridden using the environment variable:

```text
VITE_API_BASE_URL
```

The overall request flow is:

```text
React Frontend
      ↓
   REST API
      ↓
Spring Boot Controller
      ↓
     Service
      ↓
   Repository
      ↓
Spring Data JPA / Hibernate
      ↓
 PostgreSQL
```

## Development

The project is maintained as a single repository containing both the backend and frontend.

Backend development is handled using Java, Spring Boot, Spring Data JPA, and Spring Security.

Frontend development is handled using React, TypeScript, and Vite.

The application is structured around business modules to keep related functionality organized while maintaining a single deployable application.
