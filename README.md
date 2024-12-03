# Banking System - Transaction Microservice

The Transaction microservice is part of a banking system designed to handle financial transactions, such as deposits, withdrawals, transfers, and historical data, associated with customer accounts. This microservice utilizes a reactive programming approach with Spring WebFlux, integrates with MongoDB for storage. API documentation is available via Swagger/OpenAPI, and a Postman collection is provided for testing, as well as, unit tests with JUnit, Mockito. Code coverage is tracked with JaCoCo and code validation with Checkstyle.

## Table of Contents
- [Technologies and Approaches](#technologies-and-approaches)
- [UML Diagrams](#uml-diagrams)
- [Postman](#postman)
- [Swagger/OpenAPI Documentation](#swaggeropenapi-documentation)
- [Code Quality and Coverage](#code-quality-and-coverage)

## Technologies and Approaches

- **Spring Boot with WebFlux**: Enables reactive and non-blocking programming to support high-throughput operations.
- **MongoDB**: Serves as the database for storing transaction data in a flexible, document-oriented format.
- **OpenAPI/Swagger**: Provides API documentation and supports easy testing of endpoints.
- **Reactive Programming**: Ensures non-blocking I/O operations and scalability.
- **Postman**: Used for testing endpoints via a pre-configured collection.
- **JaCoCo-Checkstyle**: To verify code covergae and best practices in code style.

## UML Diagrams

The following UML diagrams detail the architecture and data flow of the Transaction Microservice:

- **Sequence Diagram**: Illustrates the flow of operations for making a deposit and withdrawal transactions and communicating with the [Account microservice](https://github.com/abengl/NTT-Project2-AccountMS).

  <img alt="UML Deposit sequence diagram" src="https://github.com/abengl/NTT-Project3-TransactionMs/blob/985c5b9c89b2abca16937ca82362dba15d26c955/src/main/resources/uml/Transaction_Microservice_Deposit_UML_Sequence_Diagram.png" width="500" height="500">

  <img alt="UML Withdrawal sequence diagram" src="https://github.com/abengl/NTT-Project3-TransactionMs/blob/985c5b9c89b2abca16937ca82362dba15d26c955/src/main/resources/uml/Transaction_Microservice_Withdrawal_UML_Sequence_Diagram.png" width="500" height="500">
  
- **Component Diagram**: Represents the overall architecture of the Transaction Microservice and its interaction with MongoDB and other services.  
<img alt="UML component diagram" src="https://github.com/abengl/NTT-Project3-TransactionMs/blob/985c5b9c89b2abca16937ca82362dba15d26c955/src/main/resources/uml/Transaction_Microservice_UML_Component_Diagram.png" width="800" height="400">

## Postman

A Postman collection is provided to test the Transaction microservice's endpoints. Follow these steps:
1. **Import the Collection**: Download or clone the repository, then import the Postman collection file located in the `/postman` directory.
2. **Import Environment Variables**: import the environment variables into Postman and set them to run with the test collection.
3. **Run Tests**: Once configured, you can execute requests to test each endpoint. The collection provides requests for creating, retrieving, updating, and deleting customer records.

## Swagger/OpenAPI Documentation

The Swagger/OpenAPI documentation provides a detailed description of each endpoint, including parameters, request bodies, and response formats.

1. Ensure the service is running locally (default: `http://localhost:8087`).
2. Open a browser and navigate to: [http://localhost:8087/v1/swagger-ui.html](http://localhost:8087/v1/swagger-ui.html)

## Code Quality and Coverage

To maintain code quality and ensure adequate test coverage, the project uses **Checkstyle** for code analysis and **JaCoCo** for test coverage reports. Follow the steps below to run these tools:

### Run Checkstyle
1. Open a terminal and navigate to the project directory.
2. Run the following command to perform a Checkstyle analysis:
   ```bash
   mvn checkstyle:check
   ```
3. Review the output in the terminal for any code style violations. The results will also be saved in the target/reports/checkstyle.html file.
   
### Run JaCoCo for Test Coverage
1. In the terminal, run the tests with coverage analysis:
   ```bash
   mvn clean test
   ```
2. Open the generated report located at `target/site/jacoco/index.html` in your browser to review coverage details.

