package com.alessandragodoy.transactionms.controller.dto;

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
		String id,
		String accountNumber,
		String transactionType,
		Double amount,
		String date,
		String originAccount,
		String destinationAccount
		) {
}
