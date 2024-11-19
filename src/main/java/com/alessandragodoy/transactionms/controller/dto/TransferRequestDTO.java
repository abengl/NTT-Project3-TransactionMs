package com.alessandragodoy.transactionms.controller.dto;

public record TransferRequestDTO(String originAccount, String destinationAccount, Double amount) {
}
