package com.alessandragodoy.transactionms.exception;

/**
 * Exception thrown when an invalid parameter is encountered.
 */
public class InvalidParameterException extends RuntimeException {
	public InvalidParameterException(String message) {
		super(message);
	}
}
