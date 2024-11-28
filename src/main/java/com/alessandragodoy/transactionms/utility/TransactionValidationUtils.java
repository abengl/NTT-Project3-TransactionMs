package com.alessandragodoy.transactionms.utility;

import com.alessandragodoy.transactionms.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;


public class TransactionValidationUtils {
	private static final Logger logger = LoggerFactory.getLogger(TransactionValidationUtils.class);
	private TransactionValidationUtils() {
	}

	public static Mono<Void> validateAccountNumber(String accountNumber) {
		if (accountNumber == null || accountNumber.trim().isEmpty()) {
			logger.error("Invalid accountNumber: {}", accountNumber);
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
			logger.error("Invalid amount: {}", amount);
			return Mono.error(new InvalidParameterException("Amount must be greater than 0"));
		}
		return Mono.empty();
	}
}
