package com.gfg.paymentappproject.TransactionService.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.paymentappproject.TransactionService.entities.Transaction;
import com.gfg.paymentappproject.TransactionService.models.TransactionCreatedResponse;
import com.gfg.paymentappproject.TransactionService.models.TransactionRequest;
import com.gfg.paymentappproject.TransactionService.models.TransactionResponse;
import com.gfg.paymentappproject.TransactionService.models.TransactionStatus;
import com.gfg.paymentappproject.TransactionService.repos.TransactionRepository;
import com.gfg.paymentappproject.WalletService.entities.WalletResponse;
import com.gfg.paymentappproject.WalletService.entities.WalletStatus;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TransactionManagerImpl implements TransactionManager{
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @SneakyThrows
    @Override
    public String create(TransactionRequest transactionRequest, String username) {
        Transaction transaction = Transaction.builder()
                .amount(transactionRequest.getAmount())
                .date(new Date())
                .fromUser(username)
                .toUser(transactionRequest.getToUser())
                .txType(transactionRequest.getTxType())
                .txId(UUID.randomUUID().toString())
                .status(TransactionStatus.PENDING)
                .build();
        transactionRepository.save(transaction);
        TransactionCreatedResponse transactionCreatedResponse =
                TransactionCreatedResponse.builder()
                        .transactionId(transaction.getTxId())
                        .fromUser(transaction.getFromUser())
                        .toUser(transaction.getToUser())
                        .amount(transaction.getAmount())
                        .build();
        kafkaTemplate.send("transactionTopic", objectMapper.writeValueAsString(transactionCreatedResponse));
        return transaction.getTxId();
    }

    @Override
    public TransactionResponse get(String transactionId) throws Exception {
        Transaction transaction = transactionRepository.findByTxId(transactionId)
                .orElseThrow(()-> new Exception("Transaction Id is not valid"));
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .txId(transaction.getTxId())
                .transactionStatus(transaction.getStatus())
                .build();
        return transactionResponse;
    }

    @Override
    @KafkaListener(topics = "wallet", groupId = "transactionService")
    public void updateStatus(String updateRequest) throws Exception {
        WalletResponse walletResponse = objectMapper.readValue(updateRequest, WalletResponse.class);
        Transaction transaction = transactionRepository.findByTxId(walletResponse.getTxId())
                .orElseThrow(()-> new Exception("Transaction Id is not valid"));
        if (walletResponse.getSuccess()==WalletStatus.SUCCESS)
            transaction.setStatus(TransactionStatus.SUCCESS);
        else
            transaction.setStatus(TransactionStatus.FAILURE);
        transactionRepository.save(transaction);
    }
}
