package com.alessandragodoy.transactionms.exception;

/**
 * Exception thrown when an external service fails.
 */
public class ExternalServiceException extends RuntimeException {
	public ExternalServiceException(String message) {
		super(message);
	}
}
