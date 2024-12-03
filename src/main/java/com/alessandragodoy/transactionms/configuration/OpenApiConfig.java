package com.alessandragodoy.transactionms.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Configuration class for OpenAPI documentation.
 * This class sets up the OpenAPI definition for the Customer Microservice.
 */
@OpenAPIDefinition(
		info = @Info(
				title = "Banking system - Transaction Microservice",
				description = "API for managing transactions with CRUD operations",
				version = "1.0.0",
				contact = @Contact(
						name = "Alessandra Godoy",
						email = "api@alessandragodoy.com"
				)
		))
public class OpenApiConfig {
}

