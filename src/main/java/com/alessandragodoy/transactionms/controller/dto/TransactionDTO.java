package com.alessandragodoy.transactionms.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Data Transfer Object for transactions.
 *
 * @param id                 the transaction ID
 * @param accountNumber      the account number associated with the transaction
 * @param transactionType    the type of transaction (e.g., deposit, withdraw, transfer)
 * @param amount             the amount involved in the transaction
 * @param date               the date of the transaction
 * @param originAccount      the charged account
 * @param destinationAccount the destination account
 **/
public record TransactionDTO(
		@Schema(description = "Unique identifier for the transaction", example = "1")
		String id,
		@Schema(description = "Primary account number", example = "A000001")
		String accountNumber,
		@Schema(description = "Type of the transaction", example = "DEPOSIT")
		String transactionType,
		@Schema(description = "Amount of the transaction", example = "100.0")
		Double amount,
		@Schema(description = "Date of the transaction", example = "2021-01-01")
		String date,
		@Schema(description = "Charged account", example = "A000001")
		String originAccount,
		@Schema(description = "Destination account", example = "A000002")
		String destinationAccount) {
}
