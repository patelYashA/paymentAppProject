package com.gfg.paymentappproject.TransactionService.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponse {
    private String txId;
    private TransactionStatus transactionStatus; //String type if output error
}
