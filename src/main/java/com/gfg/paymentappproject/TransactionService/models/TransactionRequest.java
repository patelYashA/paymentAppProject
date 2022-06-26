package com.gfg.paymentappproject.TransactionService.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest {
    private String toUser;
    private Float amount;
    private TransactionType txType;
}
