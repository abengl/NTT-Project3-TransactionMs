package com.alessandragodoy.transactionms.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A DTO for withdraw requests.
 *
 * @param originAccount the account from which the amount is to be withdrawn
 * @param amount        the amount to be withdrawn
 */
public record WithdrawRequestDTO(
		@Schema(description = "The account from which the amount is to be withdrawn", example = "A000001")
		String originAccount,
		@Schema(description = "The amount to be withdrawn", example = "100.0")
		Double amount) {
}
