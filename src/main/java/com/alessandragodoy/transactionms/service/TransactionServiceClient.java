package com.alessandragodoy.transactionms.service;

import com.alessandragodoy.transactionms.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionServiceClient {

	private final WebClient webClient;

	public Mono<Boolean> verifyAccountNumber(String accountNumber) {
		return webClient.get()
				.uri("/verify/{accountNumber}", accountNumber)
				.retrieve()
				.bodyToMono(Boolean.class)
				.onErrorMap(WebClientRequestException.class, error -> new ExternalServiceException("There is an " +
						"error" +
						" on the account verification service: " + error.getMessage()));
	}

	public Mono<Double> getAccountBalance(String accountNumber) {
		return webClient.get()
				.uri("/balance/{accountNumber}", accountNumber)
				.retrieve()
				.bodyToMono(Double.class)
				.onErrorMap(WebClientRequestException.class, error -> new ExternalServiceException(
						"There is an error on the account verification service: " + error.getMessage()));
	}

	public Mono<Void> updateAccountBalance(String accountNumber, Double amount) {
		return webClient.patch()
				.uri("/update/{accountNumber}?amount={amount}", accountNumber, amount)
				.retrieve()
				.bodyToMono(Void.class)
				.onErrorMap(WebClientRequestException.class, error -> new ExternalServiceException(
						"There is an error on the account verification service: " + error.getMessage()));
	}
}
