package com.alessandragodoy.transactionms.service;

import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing transactions in the banking system.
 */
public interface TransactionService {
	/**
	 * Lists all transactions.
	 *
	 * @return a Flux of TransactionDTO representing all transactions.
	 */
	Flux<TransactionDTO> listAllTransactions();

	/**
	 * Registers a deposit transaction.
	 *
	 * @param deposit the deposit request data transfer object.
	 * @return a Mono of TransactionDTO representing the registered deposit transaction.
	 */
	Mono<TransactionDTO> registerDeposit(DepositRequestDTO deposit);

	/**
	 * Registers a withdrawal transaction.
	 *
	 * @param withdraw the withdrawal request data transfer object.
	 * @return a Mono of TransactionDTO representing the registered withdrawal transaction.
	 */
	Mono<TransactionDTO> registerWithdraw(WithdrawRequestDTO withdraw);

	/**
	 * Registers a transfer transaction.
	 *
	 * @param transfer the transfer request data transfer object.
	 * @return a Mono of TransactionDTO representing the registered transfer transaction.
	 */
	Mono<TransactionDTO> registerTransfer(TransferRequestDTO transfer);
}
