package com.alessandragodoy.transactionms.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for deposit requests.
 *
 * @param destinationAccount the account to deposit into
 * @param amount             the amount to deposit
 */
public record DepositRequestDTO(
		@Schema(description = "Destination account number", example = "A000002")
		String destinationAccount,
		@Schema(description = "Amount to deposit", example = "100.0")
		Double amount) {
}
