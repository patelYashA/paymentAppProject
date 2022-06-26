package com.gfg.paymentappproject.TransactionService.repos;

import com.gfg.paymentappproject.TransactionService.entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> findByTxId(String txId);
}
