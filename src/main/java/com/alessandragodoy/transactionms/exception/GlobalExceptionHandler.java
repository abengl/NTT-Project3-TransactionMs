package com.alessandragodoy.transactionms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling custom exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * Handles AccountNotFoundException.
	 *
	 * @param e the exception
	 * @return the response entity with NOT_FOUND status and exception message
	 */
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Handles InsufficientFundsException.
	 *
	 * @param e the exception
	 * @return the response entity with CONFLICT status and exception message
	 */
	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	/**
	 * Handles InvalidParameterException.
	 *
	 * @param e the exception
	 * @return the response entity with BAD_REQUEST status and exception message
	 */
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<String> handleInvalidParameterException(InvalidParameterException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	/**
	 * Handles ExternalServiceException.
	 *
	 * @param e the exception
	 * @return the response entity with SERVICE_UNAVAILABLE status and exception message
	 */
	@ExceptionHandler(ExternalServiceException.class)
	public ResponseEntity<String> handleExternalServiceException(ExternalServiceException e) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
	}
}
