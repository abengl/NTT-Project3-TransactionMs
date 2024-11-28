package com.alessandragodoy.transactionms.utility;

import com.alessandragodoy.transactionms.exception.InvalidParameterException;
import reactor.core.publisher.Mono;

/**
 * Utility class for validating transaction parameters.
 */
public class TransactionValidationUtils {
	private TransactionValidationUtils() {
	}

	public static Mono<Void> validateAccountNumber(String accountNumber) {
		if (accountNumber == null || accountNumber.trim().isEmpty()) {
			return Mono.error(new InvalidParameterException("Account number is required"));
		}
		return Mono.empty();
	}

	public static Mono<Void> validateDistinctAccounts(String originAccount, String destinationAccount) {
		if (originAccount.equals(destinationAccount)) {
			return Mono.error(new InvalidParameterException("Origin and destination accounts must be different"));
		}
		return Mono.empty();
	}


	public static Mono<Void> validateAmount(Double amount) {
		if (amount == null || amount <= 0) {
			return Mono.error(new InvalidParameterException("Amount must be greater than 0"));
		}
		return Mono.empty();
	}
}
