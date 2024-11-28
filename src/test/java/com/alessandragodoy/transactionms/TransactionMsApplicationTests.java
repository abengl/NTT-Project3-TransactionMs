package com.alessandragodoy.transactionms;

import com.alessandragodoy.transactionms.controller.TransactionController;
import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import com.alessandragodoy.transactionms.model.Transaction;
import com.alessandragodoy.transactionms.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig(Transaction.class)
@SpringBootTest
class TransactionMsApplicationTests {
	@Mock
	TransactionService transactionService;
	WebTestClient webTestClient;
	List<TransactionDTO> transactions;

	@BeforeEach
	void setUp(ApplicationContext context) {
		MockitoAnnotations.openMocks(this);
		webTestClient = WebTestClient.bindToController(new TransactionController(transactionService)).build();
		transactions = new ArrayList<>();
		transactions.add(new TransactionDTO(
				"82e9c1", "A000002", "DEPOSIT", 100.0, "2024-11-28T17:53:44.238Z", "NE","A000002"
		));
		transactions.add(new TransactionDTO(
				"82e9c1", "A000003", "WITHDRAW", 100.0, "2024-11-28T17:54:09.021Z", "A000003","NE"
		));
		transactions.add(new TransactionDTO(
				"6748bf", "A000001", "TRANSFER", 100.0, "2024-11-28T19:07:46.789Z", "A000001","A000002"
		));

	}

	@Test
	@DisplayName("Test getTransactionsHistorial - returns a TransactionDTO List with historical data")
	void getTransactionsHistorial_ReturnsTransactionDTOList() {
		given(transactionService.listAllTransactions()).willReturn(Flux.fromIterable(transactions));

		webTestClient.get()
				.uri("/api/transactions")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(TransactionDTO.class)
				.hasSize(3)
				.consumeWith(response -> {
					List<TransactionDTO> responseBody = response.getResponseBody();
					assertNotNull(responseBody);
					assertEquals(transactions, responseBody);
				});
	}

	@Test
	@DisplayName("Test registerDeposit - returns a TransactionDTO with successful deposit data")
	void registerDeposit_ReturnsTransactionDTO() {
		DepositRequestDTO depositRequestDTO = new DepositRequestDTO("A000002", 100.0);
		TransactionDTO transactionResponse = transactions.get(0);
		given(transactionService.registerDeposit(depositRequestDTO)).willReturn(Mono.just(transactionResponse));

		webTestClient.post()
				.uri("/api/transactions/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(depositRequestDTO)
				.exchange()
				.expectStatus().isOk()
				.expectBody(TransactionDTO.class)
				.value(transaction -> assertEquals(transactionResponse, transaction));
	}

	@Test
	@DisplayName("Test registerWithdraw - returns a TransactionDTO with successful withdraw data")
	void registerWithdraw_ReturnsTransactionDTO() {
		WithdrawRequestDTO withdrawRequestDTO = new WithdrawRequestDTO("A000003", 100.0);
		TransactionDTO transactionResponse = transactions.get(1);
		given(transactionService.registerWithdraw(withdrawRequestDTO)).willReturn(Mono.just(transactionResponse));

		webTestClient.post()
				.uri("/api/transactions/withdraw")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(withdrawRequestDTO)
				.exchange()
				.expectStatus().isOk()
				.expectBody(TransactionDTO.class)
				.value(transaction -> assertEquals(transactionResponse, transaction));
	}

	@Test
	@DisplayName("Test registerTransfer - returns a TransactionDTO with successful transfer data")
	void registerTransfer_ReturnsTransactionDTO() {
		TransferRequestDTO transferRequestDTO = new TransferRequestDTO("A000001", "A000002", 100.0);
		TransactionDTO transactionResponse = transactions.get(2);
		given(transactionService.registerTransfer(transferRequestDTO)).willReturn(Mono.just(transactionResponse));

		webTestClient.post()
				.uri("/api/transactions/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(transferRequestDTO)
				.exchange()
				.expectStatus().isOk()
				.expectBody(TransactionDTO.class)
				.value(transaction -> assertEquals(transactionResponse, transaction));
	}

}
