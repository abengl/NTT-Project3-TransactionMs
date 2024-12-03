package com.alessandragodoy.transactionms.utility;

import com.alessandragodoy.transactionms.exception.InvalidParameterException;
import reactor.core.publisher.Mono;

/**
 * Utility class for validating transaction parameters.
 * <p>
 * This class uses the Singleton design pattern to ensure that only one instance of the utility class exists.
 */
public class TransactionValidationUtils {
	private TransactionValidationUtils() {
	}

	/**
	 * Validates the account number.
	 *
	 * @param accountNumber the account number to validate
	 * @return a Mono that completes if the account number is valid, or emits an error if invalid.
	 */
	public static Mono<Void> validateAccountNumber(String accountNumber) {
		if (accountNumber == null || accountNumber.trim().isEmpty()) {
			return Mono.error(new InvalidParameterException("Account number is required"));
		}
		return Mono.empty();
	}

	/**
	 * Validates that the origin and destination accounts are distinct.
	 *
	 * @param originAccount      the origin account number
	 * @param destinationAccount the destination account number
	 * @return a Mono that completes if the accounts are distinct, or emits an error if they are the same.
	 */
	public static Mono<Void> validateDistinctAccounts(String originAccount, String destinationAccount) {
		if (originAccount.equals(destinationAccount)) {
			return Mono.error(new InvalidParameterException("Origin and destination accounts must be different"));
		}
		return Mono.empty();
	}

	/**
	 * Validates the transaction amount.
	 *
	 * @param amount the amount to validate
	 * @return a Mono that completes if the amount is valid, or emits an error if invalid.
	 */
	public static Mono<Void> validateAmount(Double amount) {
		if (amount == null || amount <= 0) {
			return Mono.error(new InvalidParameterException("Amount must be greater than 0"));
		}
		return Mono.empty();
	}
}
