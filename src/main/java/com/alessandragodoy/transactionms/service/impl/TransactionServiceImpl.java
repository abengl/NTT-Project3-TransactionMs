package com.alessandragodoy.transactionms.service.impl;

import com.alessandragodoy.transactionms.adapter.TransactionAdapter;
import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import com.alessandragodoy.transactionms.exception.AccountNotFoundException;
import com.alessandragodoy.transactionms.exception.InsufficientFundsException;
import com.alessandragodoy.transactionms.repository.TransactionRepository;
import com.alessandragodoy.transactionms.service.TransactionService;
import com.alessandragodoy.transactionms.utility.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.alessandragodoy.transactionms.utility.TransactionValidationUtils.*;

/**
 * Implementation of the TransactionService interface.
 * This service handles the business logic for managing transactions.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	private final TransactionAdapter transactionAdapter;

	@Override
	public Flux<TransactionDTO> listAllTransactions() {
		return transactionRepository.findAll().map(transactionMapper::toTransactionDTO);
	}

	@Override
	public Mono<TransactionDTO> registerDeposit(DepositRequestDTO deposit) {
		return validateAccountNumber(deposit.destinationAccount())
				.then(validateAmount(deposit.amount()))
				.then(transactionAdapter.verifyAccountNumber(deposit.destinationAccount()))
				.flatMap(accountExists -> {
					if (!accountExists) {
						return Mono.error(new AccountNotFoundException("Account does not exist"));
					}
					return transactionRepository.save(transactionMapper.toDepositRequest(deposit))
							.flatMap(savedTransaction -> transactionAdapter.updateAccountBalance(
											deposit.destinationAccount(),
											deposit.amount())
									.thenReturn(transactionMapper.toTransactionDTO(savedTransaction)));
				});
	}

	@Override
	public Mono<TransactionDTO> registerWithdraw(WithdrawRequestDTO withdraw) {
		return validateAccountNumber(withdraw.originAccount())
				.then(validateAmount(withdraw.amount()))
				.then(transactionAdapter.verifyAccountNumber(withdraw.originAccount()))
				.flatMap(accountExists -> {
					if (!accountExists) {
						return Mono.error(new AccountNotFoundException("Account does not exist"));
					}
					return transactionAdapter.getAccountBalance(withdraw.originAccount())
							.flatMap(balance -> {
								if (balance <= withdraw.amount()) {
									return Mono.error(new InsufficientFundsException("Insufficient funds"));
								}
								return transactionRepository.save(transactionMapper.toWithdrawRequest(withdraw))
										.flatMap(savedTransaction -> transactionAdapter.updateAccountBalance(
														withdraw.originAccount(),
														-withdraw.amount())
												.thenReturn(transactionMapper.toTransactionDTO(savedTransaction)));
							});
				});
	}

	@Override
	public Mono<TransactionDTO> registerTransfer(TransferRequestDTO transfer) {
		return validateAccountNumber(transfer.originAccount())
				.then(validateAccountNumber(transfer.destinationAccount()))
				.then(validateAmount(transfer.amount()))
				.then(validateDistinctAccounts(transfer.originAccount(), transfer.destinationAccount()))
				.then(Mono.zip(
						transactionAdapter.verifyAccountNumber(transfer.originAccount()),
						transactionAdapter.verifyAccountNumber(transfer.destinationAccount())))
				.flatMap(accounts -> {
					boolean originExists = accounts.getT1();
					boolean destinationExists = accounts.getT2();
					if (!originExists || !destinationExists) {
						return Mono.error(new AccountNotFoundException("One or both accounts do not exist"));
					}
					return transactionAdapter.getAccountBalance(transfer.originAccount())
							.flatMap(balance -> {
								if (balance < transfer.amount()) {
									return Mono.error(new InsufficientFundsException("Insufficient funds"));
								}
								return transactionRepository.save(transactionMapper.toTransferRequest(transfer))
										.flatMap(savedTransaction -> Mono.when(
														transactionAdapter.updateAccountBalance(transfer.originAccount(),
																-transfer.amount()),
														transactionAdapter.updateAccountBalance(
																transfer.destinationAccount(), transfer.amount())
												)
												.thenReturn(transactionMapper.toTransactionDTO(savedTransaction)));
							});
				});
	}

}

