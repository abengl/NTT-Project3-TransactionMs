package com.alessandragodoy.transactionms.controller.dto;

public record TransactionDTO(String id, String accountNumber, String transactionType, Double amount, String date,
							 String originAccount, String destinationAccount) {
}
