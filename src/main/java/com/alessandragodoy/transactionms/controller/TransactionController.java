package com.alessandragodoy.transactionms.controller;

import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import com.alessandragodoy.transactionms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller for handling transaction-related requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
	private final TransactionService transactionService;

	/**
	 * Retrieves the transaction history.
	 *
	 * @return a ResponseEntity containing a Flux of TransactionDTO
	 */
	@GetMapping
	public ResponseEntity<Flux<TransactionDTO>> getTransactionsHistorial() {
		return ResponseEntity.ok(transactionService.listAllTransactions());
	}

	/**
	 * Registers a deposit transaction.
	 *
	 * @param depositRequest the deposit request data transfer object
	 * @return a ResponseEntity containing a Mono of TransactionDTO
	 */
	@PostMapping("/deposit")
	public ResponseEntity<Mono<TransactionDTO>> registerDeposit(@RequestBody DepositRequestDTO depositRequest) {
		return ResponseEntity.ok(transactionService.registerDeposit(depositRequest));
	}

	/**
	 * Registers a withdrawal transaction.
	 *
	 * @param withdrawRequest the withdrawal request data transfer object
	 * @return a ResponseEntity containing a Mono of TransactionDTO
	 */
	@PostMapping("/withdraw")
	public ResponseEntity<Mono<TransactionDTO>> registerWithdraw(@RequestBody WithdrawRequestDTO withdrawRequest) {
		return ResponseEntity.ok(transactionService.registerWithdraw(withdrawRequest));
	}

	/**
	 * Registers a transfer transaction.
	 *
	 * @param transferRequest the transfer request data transfer object
	 * @return a ResponseEntity containing a Mono of TransactionDTO
	 */
	@PostMapping("/transfer")
	public ResponseEntity<Mono<TransactionDTO>> registerTransfer(@RequestBody TransferRequestDTO transferRequest) {
		return ResponseEntity.ok(transactionService.registerTransfer(transferRequest));
	}

}
