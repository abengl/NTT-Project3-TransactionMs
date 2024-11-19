package com.alessandragodoy.transactionms.controller.dto;

/**
 * A DTO for withdraw requests.
 *
 * @param originAccount the account from which the amount is to be withdrawn
 * @param amount        the amount to be withdrawn
 */
public record WithdrawRequestDTO(String originAccount, Double amount) {
}
