package com.example.bank.service;

import com.example.bank.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    /**
     * interface for performing transaction
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @return Account
     * @throws Exception Account not fount or insufficient balance
     */
    Account transferFunds(Long fromAccount, Long toAccount, int amount) throws Exception;
}
