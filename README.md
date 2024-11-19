# Banking System - Transaction Microservice

The Transaction microservice is part of a banking system designed to handle financial transactions, such as deposits, withdrawals, transfers, and historical data, associated with customer accounts. This microservice utilizes a reactive programming approach with Spring WebFlux, integrates with MongoDB for storage. API documentation is available via Swagger/OpenAPI, and a Postman collection is provided for testing.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies and Approaches](#technologies-and-approaches)
- [UML Diagrams](#uml-diagrams)
- [Running the Postman Collection](#running-the-postman-collection)
- [Accessing Swagger/OpenAPI Documentation](#accessing-swaggeropenapi-documentation)

## Project Overview

The Transaction microservice is a standalone module within the broader banking system. It manages:

- Transactions (deposits, withdrawals, and account-to-account transfers)
- Transaction history retrieval and management

This microservice ensures high performance and scalability through the use of reactive programming principles.

## Technologies and Approaches

- **Spring Boot with WebFlux**: Enables reactive and non-blocking programming to support high-throughput operations.
- **MongoDB**: Serves as the database for storing transaction data in a flexible, document-oriented format.
- **OpenAPI/Swagger**: Provides API documentation and supports easy testing of endpoints.
- **Reactive Programming**: Ensures non-blocking I/O operations and scalability.
- **Postman**: Used for testing endpoints via a pre-configured collection.

## UML Diagrams

The following UML diagrams detail the architecture and data flow of the Transaction Microservice:

- **Sequence Diagram**: Illustrates the flow of operations for making a deposit and withdrawal transactions and communicating with the [Account microservice](https://github.com/abengl/NTT-Project2-AccountMS).

  <img alt="UML Deposit sequence diagram" src="https://github.com/abengl/NTT-Project3-TransactionMs/blob/985c5b9c89b2abca16937ca82362dba15d26c955/src/main/resources/uml/Transaction_Microservice_Deposit_UML_Sequence_Diagram.png" width="500" height="500">

  <img alt="UML Withdrawal sequence diagram" src="https://github.com/abengl/NTT-Project3-TransactionMs/blob/985c5b9c89b2abca16937ca82362dba15d26c955/src/main/resources/uml/Transaction_Microservice_Withdrawal_UML_Sequence_Diagram.png" width="500" height="500">
  
- **Component Diagram**: Represents the overall architecture of the Transaction Microservice and its interaction with MongoDB and other services.  
<img alt="UML component diagram" src="https://github.com/abengl/NTT-Project3-TransactionMs/blob/985c5b9c89b2abca16937ca82362dba15d26c955/src/main/resources/uml/Transaction_Microservice_UML_Component_Diagram.png" width="800" height="500">

## Running the Postman Collection

A Postman collection is provided to test the Transaction microservice's endpoints. Follow these steps:

1. **Import the Collection**: Download or clone the repository and import the Postman collection file located in the `/postman` directory.
2. **Configure Environment Variables**: Ensure that Postman environment variables for service URL (e.g., `http://localhost:8088`) and any other required parameters are correctly set.
3. **Run Tests**: The collection includes requests for:
   - Creating transactions (deposits, withdrawals, transfers)
   - Retrieving transaction histories

## Accessing Swagger/OpenAPI Documentation

The Swagger/OpenAPI documentation provides a detailed description of each endpoint, including parameters, request bodies, and response formats.

1. Ensure the service is running locally (default: `http://localhost:8088`).
2. Open a browser and navigate to: `http://localhost:8088/swagger-ui.html`.

