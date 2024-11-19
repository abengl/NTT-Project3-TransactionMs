package com.alessandragodoy.transactionms.controller.dto;

/**
 * DTO for deposit requests.
 *
 * @param destinationAccount the account to deposit into
 * @param amount             the amount to deposit
 */
public record DepositRequestDTO(String destinationAccount, Double amount) {
}
