package com.alessandragodoy.transactionms.repository;

import com.alessandragodoy.transactionms.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing `Transaction` entities in MongoDB.
 */
@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
