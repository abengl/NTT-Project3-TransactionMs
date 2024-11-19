package com.alessandragodoy.transactionms.service;

import com.alessandragodoy.transactionms.controller.dto.DepositRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.TransactionDTO;
import com.alessandragodoy.transactionms.controller.dto.TransferRequestDTO;
import com.alessandragodoy.transactionms.controller.dto.WithdrawRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
	Flux<TransactionDTO> listAllTransactions();
	Mono<TransactionDTO> registerDeposit(DepositRequestDTO deposit);
	Mono<TransactionDTO> registerWithdraw(WithdrawRequestDTO withdraw);
	Mono<TransactionDTO> registerTransfer(TransferRequestDTO transfer);

}
