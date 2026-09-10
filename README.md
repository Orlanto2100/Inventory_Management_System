# Inventory System

A full-stack inventory management system built as a **modular monolith**, with a Java/Spring Boot backend and a React frontend.

The system manages products, warehouses, storage locations, customers, vendors, inventory, stock movements, purchase orders, and sales orders.

## Tech Stack

| Technology | Purpose |
| --- | --- |
| Java 21 | Backend |
| Spring Boot | Backend framework |
| Spring Data JPA | Data persistence |
| Spring Security | Authentication and security |
| PostgreSQL | Database |
| Maven | Backend build and dependency management |
| React | Frontend |
| TypeScript | Frontend development |
| Vite | Frontend build tool |

## Architecture

The application follows a **modular monolith** architecture.

Each business domain is organized into its own module within the backend application.

The backend separates responsibilities into controllers, services, repositories, entities, DTOs, and mappers where applicable.

The frontend follows a feature-based structure, with pages organized around the same business domains as the backend.

The backend and frontend are maintained together in a single repository.

## Modules

| Module | Description |
| --- | --- |
| User | Manages system users |
| Product | Manages products and product information |
| Vendor | Manages product suppliers |
| Customer | Manages customers |
| Warehouse | Manages warehouses |
| Location | Manages specific storage locations within warehouses |
| Inventory | Represents the current stock state |
| Stock Movement | Records changes to stock over time |
| Purchase Order | Represents orders for incoming products |
| Sales Order | Represents orders for outgoing products |

## Inventory and Stock Movement

The system separates the **current inventory state** from **stock movement history**.

`Inventory` represents the current quantity of a product within a warehouse or location.

`Stock Movement` records changes to inventory over time, such as:

- Incoming stock
- Outgoing stock
- Transfers
- Stock adjustments

This separation allows the system to maintain both the current stock state and a history of changes made to inventory.

## Backend

The backend is built with **Java 21 and Spring Boot**.

It provides REST APIs for the inventory system and handles:

- Business logic
- Data persistence
- Validation
- Authentication and security
- Inventory-related operations
- Order management
- Stock movement tracking

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
