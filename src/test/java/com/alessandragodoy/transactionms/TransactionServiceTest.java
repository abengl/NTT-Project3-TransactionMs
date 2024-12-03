package com.alessandragodoy.transactionms;


import com.alessandragodoy.transactionms.adapter.TransactionAdapter;
import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import com.alessandragodoy.transactionms.model.Transaction;
import com.alessandragodoy.transactionms.model.TransactionType;
import com.alessandragodoy.transactionms.repository.TransactionRepository;
import com.alessandragodoy.transactionms.service.impl.TransactionServiceImpl;
import com.alessandragodoy.transactionms.utility.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	List<TransactionDTO> transactions = new ArrayList<>();
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private TransactionMapper transactionMapper;
	@Mock
	private TransactionAdapter transactionAdapter;
	@InjectMocks
	private TransactionServiceImpl transactionService;

	@BeforeEach
	void setUp() {
		transactions.add(new TransactionDTO("000001", "A000001", "DEPOSIT", 100.0, "2024-12-03T17:53:44.238Z", "NE",
				"A000001"));
		transactions.add(new TransactionDTO(
				"000002", "A000001", "WITHDRAW", 100.0, "2024-11-28T17:54:09.021Z", "A000001", "NE"
		));
		transactions.add(new TransactionDTO(
				"000003", "A000001", "TRANSFER", 100.0, "2024-11-28T17:53:44.238Z", "A000001", "A000002"
		));
	}

	@Test
	@DisplayName("Test listAllTransactions - Success operation")
	void listAllTransactions_Success() {
		Transaction transaction =
				Transaction.builder().id("000004").primaryAccount("A000001").transactionType(TransactionType.DEPOSIT)
						.amount(100.0).date(new Date()).originAccount("NE").destinationAccount("A000001").build();

		when(transactionRepository.findAll()).thenReturn(Flux.just(transaction));
		when(transactionMapper.toTransactionDTO(transaction)).thenReturn(transactions.get(0));

		StepVerifier.create(transactionService.listAllTransactions())
				.expectNext(transactions.get(0))
				.verifyComplete();

		verify(transactionRepository).findAll();
		verify(transactionMapper).toTransactionDTO(transaction);
	}

	@Test
	@DisplayName("Test registerDeposit - Success operation")
	void registerDeposit_Success() {
		DepositRequestDTO depositRequest = new DepositRequestDTO("A000001", 100.0);
		Transaction transaction =
				Transaction.builder().id("000001").primaryAccount("A000001").transactionType(TransactionType.DEPOSIT)
						.amount(100.0).date(new Date()).originAccount("NE").destinationAccount("A000001").build();

		when(transactionAdapter.verifyAccountNumber(depositRequest.destinationAccount())).thenReturn(Mono.just(true));
		when(transactionMapper.toDepositRequest(depositRequest)).thenReturn(transaction);
		when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
		when(transactionAdapter.updateAccountBalance("A000001", 100.0)).thenReturn(Mono.empty());
		when(transactionMapper.toTransactionDTO(transaction)).thenReturn(transactions.get(0));

		StepVerifier.create(transactionService.registerDeposit(depositRequest))
				.expectNext(transactions.get(0))
				.verifyComplete();

		verify(transactionAdapter).verifyAccountNumber("A000001");
		verify(transactionRepository).save(transaction);
		verify(transactionAdapter).updateAccountBalance("A000001", 100.0);
		verify(transactionMapper).toTransactionDTO(transaction);
	}

	@Test
	@DisplayName("Test registerWithdraw - Success operation")
	void registerWithdraw_Success() {
		WithdrawRequestDTO withdrawRequest = new WithdrawRequestDTO("A000001", 100.0);
		Transaction transaction =
				Transaction.builder().id("000002").primaryAccount("A000001").transactionType(TransactionType.WITHDRAW)
						.amount(100.0).date(new Date()).originAccount("A000001").destinationAccount("NE").build();

		when(transactionAdapter.verifyAccountNumber(withdrawRequest.originAccount())).thenReturn(Mono.just(true));
		when(transactionAdapter.getAccountBalance(withdrawRequest.originAccount())).thenReturn(Mono.just(1000.0));
		when(transactionMapper.toWithdrawRequest(withdrawRequest)).thenReturn(transaction);
		when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
		when(transactionAdapter.updateAccountBalance("A000001", -100.0)).thenReturn(Mono.empty());
		when(transactionMapper.toTransactionDTO(transaction)).thenReturn(transactions.get(1));

		StepVerifier.create(transactionService.registerWithdraw(withdrawRequest))
				.expectNext(transactions.get(1))
				.verifyComplete();

		verify(transactionAdapter).verifyAccountNumber("A000001");
		verify(transactionRepository).save(transaction);
		verify(transactionAdapter).updateAccountBalance("A000001", -100.0);
		verify(transactionMapper).toTransactionDTO(transaction);
	}

	@Test
	@DisplayName("Test registerTransfer - Success operation")
	void registerTransfer_Success() {
		TransferRequestDTO transferRequest = new TransferRequestDTO("A000001", "A000002", 100.0);
		Transaction transaction =
				Transaction.builder().id("000003").primaryAccount("A000001").transactionType(TransactionType.TRANSFER)
						.amount(100.0).date(new Date()).originAccount("A000001").destinationAccount("A000002").build();

		when(transactionAdapter.verifyAccountNumber(transferRequest.originAccount())).thenReturn(Mono.just(true));
		when(transactionAdapter.verifyAccountNumber(transferRequest.destinationAccount())).thenReturn(Mono.just(true));

		when(transactionAdapter.getAccountBalance(transferRequest.originAccount())).thenReturn(Mono.just(1000.0));
		when(transactionMapper.toTransferRequest(transferRequest)).thenReturn(transaction);
		when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

		when(transactionAdapter.updateAccountBalance("A000001", -100.0)).thenReturn(Mono.empty());
		when(transactionAdapter.updateAccountBalance("A000002", 100.0)).thenReturn(Mono.empty());

		when(transactionMapper.toTransactionDTO(transaction)).thenReturn(transactions.get(2));

		StepVerifier.create(transactionService.registerTransfer(transferRequest))
				.expectNext(transactions.get(2))
				.verifyComplete();

		verify(transactionAdapter).verifyAccountNumber("A000001");
		verify(transactionAdapter).verifyAccountNumber("A000002");
		verify(transactionRepository).save(transaction);
		verify(transactionAdapter).updateAccountBalance("A000001", -100.0);
		verify(transactionAdapter).updateAccountBalance("A000002", 100.0);
		verify(transactionMapper).toTransactionDTO(transaction);
	}

}

