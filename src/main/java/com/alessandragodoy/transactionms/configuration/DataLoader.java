package com.alessandragodoy.transactionms.configuration;

import com.alessandragodoy.transactionms.model.Transaction;
import com.alessandragodoy.transactionms.model.TransactionType;
import com.alessandragodoy.transactionms.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);
	private final TransactionRepository transactionRepository;

	@Override
	public void run(String... args) {
		transactionRepository.count()
				.flatMapMany(count -> {
					if (count == 0) {
						LOGGER.info("No transactions found, creating initial transactions...");
						String primaryAccount = "A00000000001";
						String secondaryAccount = "A00000000002";
						List<Transaction> initialTransactions = List.of(
								Transaction.builder().id(null).primaryAccount(primaryAccount)
										.transactionType(TransactionType.DEPOSIT).amount(100.0).date(new Date())
										.originAccount("NE").destinationAccount(primaryAccount).build(),
								Transaction.builder().id(null).primaryAccount(primaryAccount)
										.transactionType(TransactionType.WITHDRAW).amount(100.0).date(new Date())
										.originAccount(secondaryAccount).destinationAccount("NE").build(),
								Transaction.builder().id(null).primaryAccount(secondaryAccount)
										.transactionType(TransactionType.TRANSFER).amount(100.0).date(new Date())
										.originAccount(primaryAccount).destinationAccount(secondaryAccount).build()
						);
						return transactionRepository.saveAll(initialTransactions)
								.doOnComplete(() -> LOGGER.info("Initial transactions added to the database."));
					} else {
						LOGGER.info("Transactions already exist, skipping initialization.");
						return Flux.empty();
					}
				})
				.subscribe();
	}
}
