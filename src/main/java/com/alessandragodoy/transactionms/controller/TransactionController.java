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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
	private final TransactionService transactionService;

	@GetMapping
	public ResponseEntity<Flux<TransactionDTO>> getTransactionsHistorial() {
		return ResponseEntity.ok(transactionService.listAllTransactions());
	}

	@PostMapping("/deposit")
	public ResponseEntity<Mono<TransactionDTO>> registerDeposit(@RequestBody DepositRequestDTO depositRequest) {
		return ResponseEntity.ok(transactionService.registerDeposit(depositRequest));
	}
	@PostMapping("/withdraw")
	public ResponseEntity<Mono<TransactionDTO>> registerWithdraw(@RequestBody WithdrawRequestDTO withdrawRequest) {
		return ResponseEntity.ok(transactionService.registerWithdraw(withdrawRequest));
	}
	@PostMapping("/transfer")
	public ResponseEntity<Mono<TransactionDTO>> registerTransfer(@RequestBody TransferRequestDTO transferRequest) {
		return ResponseEntity.ok(transactionService.registerTransfer(transferRequest));
	}

}
