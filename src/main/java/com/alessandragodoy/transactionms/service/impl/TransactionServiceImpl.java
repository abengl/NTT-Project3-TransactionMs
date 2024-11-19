package com.alessandragodoy.transactionms.service.impl;

import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import com.alessandragodoy.transactionms.exception.AccountNotFoundException;
import com.alessandragodoy.transactionms.exception.InsufficientFundsException;
import com.alessandragodoy.transactionms.exception.InvalidParameterException;
import com.alessandragodoy.transactionms.repository.TransactionRepository;
import com.alessandragodoy.transactionms.service.TransactionService;
import com.alessandragodoy.transactionms.utility.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	private final WebClient webClient;

	/* Transaction Service methods */
	@Override
	public Flux<TransactionDTO> listAllTransactions() {
		return transactionRepository.findAll().map(transactionMapper::toTransactionDTO);
	}

	@Override
	public Mono<TransactionDTO> registerDeposit(DepositRequestDTO deposit) {
		if (deposit.destinationAccount() == null && deposit.amount() == null) {
			throw new InvalidParameterException("Account and amount are required");
		}
		return verifyAccountNumber(deposit.destinationAccount())
				.flatMap(accountExists -> {
					if (!accountExists) {
						return Mono.error(new AccountNotFoundException("Account does not exist"));
					}
					if (deposit.amount() <= 0) {
						return Mono.error(new InvalidParameterException("Amount must be greater than 0"));
					}
					return transactionRepository.save(transactionMapper.toDepositRequest(deposit))
							.flatMap(savedTransaction -> updateAccountBalance(deposit.destinationAccount(),
									deposit.amount())
									.thenReturn(transactionMapper.toTransactionDTO(savedTransaction)));
				});
	}

	@Override
	public Mono<TransactionDTO> registerWithdraw(WithdrawRequestDTO withdraw) {
		if (withdraw.originAccount() == null && withdraw.amount() == null) {
			throw new InvalidParameterException("Account and amount are required");
		}
		return verifyAccountNumber(withdraw.originAccount())
				.flatMap(accountExists -> {
					if (!accountExists) {
						return Mono.error(new AccountNotFoundException("Account does not exist"));
					}
					if (withdraw.amount() <= 0) {
						return Mono.error(new InvalidParameterException("Amount must be greater than 0"));
					}
					return getAccountBalance(withdraw.originAccount())
							.flatMap(balance -> {
								if (balance <= withdraw.amount()) {
									return Mono.error(new InsufficientFundsException("Insufficient funds"));
								}
								return transactionRepository.save(transactionMapper.toWithdrawRequest(withdraw))
										.flatMap(savedTransaction -> updateAccountBalance(withdraw.originAccount(),
												-withdraw.amount())
												.thenReturn(transactionMapper.toTransactionDTO(savedTransaction)));
							});
				});
	}

	@Override
	public Mono<TransactionDTO> registerTransfer(TransferRequestDTO transfer) {
		if (transfer.originAccount() == null && transfer.destinationAccount() == null && transfer.amount() == null) {
			throw new InvalidParameterException("Account and amount are required");
		}
		if (transfer.originAccount().equals(transfer.destinationAccount())) {
			throw new InvalidParameterException("Origin and destination accounts must be different");
		}
		return verifyAccountNumber(transfer.destinationAccount())
				.flatMap(destinationAccountExists -> {
					if (!destinationAccountExists) {
						return Mono.error(new AccountNotFoundException("Destination account does not exist"));
					}
					if (transfer.amount() <= 0) {
						return Mono.error(new InvalidParameterException("Amount must be greater than 0"));
					}
					return getAccountBalance(transfer.originAccount())
							.flatMap(balance -> {
								if (balance < transfer.amount()) {
									return Mono.error(new InsufficientFundsException("Insufficient funds"));
								}
								return transactionRepository.save(transactionMapper.toTransferRequest(transfer))
										.flatMap(savedTransaction ->
												Mono.when(
														updateAccountBalance(transfer.originAccount(),
																-transfer.amount()),
														updateAccountBalance(transfer.destinationAccount(),
																transfer.amount())
												).thenReturn(transactionMapper.toTransactionDTO(savedTransaction)));
							});
				});
	}

	private Mono<Double> getAccountBalance(String accountNumber) {
		return webClient.get()
				.uri("/balance/{accountNumber}", accountNumber)
				.retrieve()
				.bodyToMono(Double.class)
				.doOnError(error -> System.err.println("Error al obtener el balance: " + error.getMessage()));
	}

	/* Helper methods */
	private Mono<Boolean> verifyAccountNumber(String accountNumber) {
		return webClient.get()
				.uri("/verify/{accountNumber}", accountNumber)
				.retrieve()
				.bodyToMono(Boolean.class)
				.doOnError(
						error -> System.err.println("Error al verificar el número de cuenta: " + error.getMessage()));
	}

	private Mono<Void> updateAccountBalance(String accountNumber, Double amount) {
		return webClient.patch()
				.uri("/update/{accountNumber}?amount={amount}", accountNumber, amount)
				.retrieve()
				.bodyToMono(Void.class)
				.doOnError(error -> System.err.println("Error al actualizar el balance: " + error.getMessage()));
	}
}

