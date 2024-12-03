package com.alessandragodoy.transactionms.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for transfer requests.
 *
 * @param originAccount      the account from which the transfer originates
 * @param destinationAccount the account to which the transfer is made
 * @param amount             the amount to be transferred
 */
public record TransferRequestDTO(
		@Schema(description = "Account number from which the transfer originates", example = "A000001")
		String originAccount,
		@Schema(description = "Account number to which the transfer is made", example = "A000002")
		String destinationAccount,
		@Schema(description = "Amount to be transferred", example = "100.0")
		Double amount) {
}
