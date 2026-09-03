# Inventory Management System

A backend inventory system built as a modular monolith using Java and Spring Boot, with PostgreSQL as the database.

## Tech Stack

| Technology      | Purpose                         |
| --------------- | ------------------------------- |
| Java 21         | Backend                         |
| Spring Boot     | Application framework           |
| Spring Data JPA | Data persistence                |
| PostgreSQL      | Database                        |
| Maven           | Build and dependency management |
| Docker          | Containerization                |

## Architecture

The application follows a **modular monolith** architecture.

Each business domain is organized into its own module within the application.

## Domains

| Domain         | Description                                |
| -------------- | ------------------------------------------ |
| User           | Represents users of the system             |
| Product        | Represents products stored and sold        |
| Vendor         | Represents product suppliers               |
| Location       | Represents specific storage locations      |
| Warehouse      | Represents warehouses containing inventory |
| Customer       | Represents customers                       |
| Stock Movement | Records changes to stock over time         |
| Inventory      | Represents the current stock state         |
| Sales Order    | Represents orders for outgoing products    |
| Purchase Order | Represents orders for incoming products    |

## Inventory and Stock Movement

The system separates the **current inventory state** from **stock movement history**.

`Inventory` represents the current stock quantity of products within warehouses and locations.

`Stock Movement` records changes to stock over time, such as incoming, outgoing, transferred, or adjusted quantities.

## Getting Started

### Prerequisites

* Java 21
* Maven
* PostgreSQL
* Docker *(optional)*

###Database

Create a PostgreSQL database named:

inventory_db

Configure the database connection in application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=your_username
spring.datasource.password=your_password

The application currently uses Hibernate to create the database schema from the JPA entities when the application starts.

Run the Application

Using Maven:

./mvnw spring-boot:run

The application runs on:

http://localhost:8080
