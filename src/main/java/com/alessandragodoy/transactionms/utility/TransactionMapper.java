package com.alessandragodoy.transactionms.utility;

import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import com.alessandragodoy.transactionms.model.Transaction;
import com.alessandragodoy.transactionms.model.TransactionType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * Mapper class for converting between Transaction entities and various DTOs.
 */
@Component
public class TransactionMapper {

	public TransactionDTO toTransactionDTO(Transaction transaction) {
		return Optional.ofNullable(transaction)
				.map(t -> new TransactionDTO(
						t.getId(),
						t.getPrimaryAccount(),
						t.getTransactionType().name(),
						t.getAmount(),
						formatDate(t.getDate()),
						t.getOriginAccount(),
						t.getDestinationAccount()
				))
				.orElse(null);
	}

	private String formatDate(Date date) {
		return date != null ? date.toInstant().toString() : null;
	}

	public Transaction toDepositRequest(DepositRequestDTO depositRequestDTO) {
		return Optional.ofNullable(depositRequestDTO)
				.map(dto -> new Transaction(
						null,
						dto.destinationAccount(),
						TransactionType.DEPOSIT,
						dto.amount(),
						new Date(),
						"NE",
						dto.destinationAccount()
				))
				.orElse(null);
	}

	public Transaction toWithdrawRequest(WithdrawRequestDTO withdrawRequestDTO) {
		return Optional.ofNullable(withdrawRequestDTO)
				.map(dto -> new Transaction(
						null,
						dto.originAccount(),
						TransactionType.WITHDRAW,
						dto.amount(),
						new Date(),
						dto.originAccount(),
						"NE"
				))
				.orElse(null);
	}

	public Transaction toTransferRequest(TransferRequestDTO transferRequestDTO) {
		return Optional.ofNullable(transferRequestDTO)
				.map(dto -> new Transaction(
						null,
						dto.originAccount(),
						TransactionType.TRANSFER,
						dto.amount(),
						new Date(),
						dto.originAccount(),
						dto.destinationAccount()
				))
				.orElse(null);
	}
}

