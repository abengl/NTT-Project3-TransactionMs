package com.alessandragodoy.transactionms.service;

import com.alessandragodoy.transactionms.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

/**
 * Service client for handling transactions.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceClient {

	private final WebClient webClient;

	/**
	 * Verifies if the given account number is valid.
	 *
	 * @param accountNumber the account number to verify
	 * @return a Mono emitting true if the account number is valid, false otherwise
	 */
	public Mono<Boolean> verifyAccountNumber(String accountNumber) {
		return webClient.get().uri("/verify/{accountNumber}", accountNumber).retrieve().bodyToMono(Boolean.class)
				.onErrorMap(WebClientRequestException.class, error -> new ExternalServiceException(
						"There is an " + "error" + " on the account verification service: " + error.getMessage()));
	}

	/**
	 * Retrieves the balance of the given account number.
	 *
	 * @param accountNumber the account number to retrieve the balance for
	 * @return a Mono emitting the account balance
	 */
	public Mono<Double> getAccountBalance(String accountNumber) {
		return webClient.get().uri("/balance/{accountNumber}", accountNumber).retrieve().bodyToMono(Double.class)
				.onErrorMap(WebClientRequestException.class, error -> new ExternalServiceException(
						"There is an error on the account verification service: " + error.getMessage()));
	}

	/**
	 * Updates the balance of the given account number.
	 *
	 * @param accountNumber the account number to update the balance for
	 * @param amount        the amount to update the balance by
	 * @return a Mono indicating completion
	 */
	public Mono<Void> updateAccountBalance(String accountNumber, Double amount) {
		return webClient.patch().uri("/update/{accountNumber}?amount={amount}", accountNumber, amount).retrieve()
				.bodyToMono(Void.class).onErrorMap(WebClientRequestException.class,
						error -> new ExternalServiceException(
								"There is an error on the account verification service: " + error.getMessage()));
	}
}
