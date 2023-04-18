package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    /**
     * Interface to get acoount Details
     * @param id
     * @return Optional<Account>
     * @throws Exception Account not found exception
     */
    Optional<Account> getAccountDetails(Long id) throws Exception;

    /**
     * Interface to get last 20 transactions
     * @param id
     * @return Optional<List<Transaction>>
     * @throws Exception Account not found exception
     */
    Optional<List<Transaction>> getMiniStatement(Long id) throws Exception;
}
