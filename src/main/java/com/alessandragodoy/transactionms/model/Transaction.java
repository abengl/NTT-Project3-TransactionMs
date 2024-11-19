package com.alessandragodoy.transactionms.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Represents a transaction in the banking system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transaction")
public class Transaction {
    @Id
    private String id;

    @Field("primary_account")
    private String primaryAccount;

    @NotNull
    @Field("transaction_type")
    private TransactionType transactionType;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Transaction amount must be greater than zero.")
    @Field("amount")
    private Double amount;

    @NotNull
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @Field("date")
    private Date date;

    @NotNull
    @Field("origin_account")
    private String originAccount;

    @Field("destination_account")
    private String destinationAccount;
}
