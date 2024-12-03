package com.alessandragodoy.transactionms.adapter;

import reactor.core.publisher.Mono;

/**
 * TransactionAdapter interface for handling transaction-related operations.
 * <p>
 * This interface follows the Adapter design pattern to adapt the interface of a class
 * into another interface that a client expects.
 * </p>
 */
public interface TransactionAdapter {
	Mono<Boolean> verifyAccountNumber(String accountNumber);
	Mono<Double> getAccountBalance(String accountNumber);
	Mono<Void> updateAccountBalance(String accountNumber, Double amount);

}
