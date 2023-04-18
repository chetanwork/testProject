package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    /**
     * This method return the account details if correct account number is passed
     * @param id
     * @return Optional<Account>
     * @throws Exception Account not found Exception
     */
    @Override
    public Optional<Account> getAccountDetails(Long id) throws Exception {
        Optional<Account> accountDetails = accountRepository.findByaccountNumber(id);
        if (accountDetails.isPresent()) {
            return accountDetails;
        }
        throw new Exception("Please check account Number " + id + ", " );
    }

    /**
     * This method return last 20 transaction if correct account number is passed
     * @param id
     * @return Optional<List<Transaction>>
     * @throws Exception Account not found Exception
     */
    public Optional<List<Transaction>> getMiniStatement(Long id) throws Exception{
        Optional<Account> miniStatement = accountRepository.findByaccountNumber(id);
        if (miniStatement.isPresent()) {
                    List<Transaction> transactions = miniStatement.get().getTransactions()
                    .stream()
                    .limit(20)
                    .collect(Collectors.toList());
            if(!transactions.isEmpty()) {
                Collections.reverse(transactions);
                return Optional.of(transactions);
            }
        }
        throw new Exception("Please check account Number " + id + ", " );
    }
}