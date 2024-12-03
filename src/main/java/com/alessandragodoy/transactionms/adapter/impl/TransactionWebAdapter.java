package com.alessandragodoy.transactionms.adapter.impl;

import com.alessandragodoy.transactionms.adapter.TransactionAdapter;
import com.alessandragodoy.transactionms.adapter.TransactionServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Implementation of the TransactionAdapter interface using a web client.
 */
@Component
@RequiredArgsConstructor
public class TransactionWebAdapter implements TransactionAdapter {
	private final TransactionServiceClient transactionServiceClient;

	@Override
	public Mono<Boolean> verifyAccountNumber(String accountNumber) {
		return transactionServiceClient.verifyAccountNumber(accountNumber);
	}

	@Override
	public Mono<Double> getAccountBalance(String accountNumber) {
		return transactionServiceClient.getAccountBalance(accountNumber);
	}

	@Override
	public Mono<Void> updateAccountBalance(String accountNumber, Double amount) {
		return transactionServiceClient.updateAccountBalance(accountNumber, amount);
	}
}
