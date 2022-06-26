package com.gfg.paymentappproject.TransactionService.manager;

import com.gfg.paymentappproject.TransactionService.models.TransactionRequest;
import com.gfg.paymentappproject.TransactionService.models.TransactionResponse;
import com.gfg.paymentappproject.TransactionService.models.TransactionStatus;

public interface TransactionManager {
    String create(TransactionRequest transactionRequest, String username);
    TransactionResponse get(String transactionId) throws Exception;
    void updateStatus(String updateRequest) throws Exception;
}
