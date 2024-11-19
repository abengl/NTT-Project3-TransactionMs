package com.alessandragodoy.transactionms.controller.dto;

/**
 * DTO for transfer requests.
 *
 * @param originAccount      the account from which the transfer originates
 * @param destinationAccount the account to which the transfer is made
 * @param amount             the amount to be transferred
 */
public record TransferRequestDTO(String originAccount, String destinationAccount, Double amount) {
}
