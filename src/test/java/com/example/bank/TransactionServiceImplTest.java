package com.example.bank;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.service.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService = new TransactionServiceImpl();

    private Account toAccount;
    private Account fromAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fromAccount = new Account();
        fromAccount.setAccountNumber(111L);
        fromAccount.setBalance(1000);
        List<Transaction> listOfTransaction= new ArrayList<>();
        Transaction transactions = new Transaction();
        transactions.setAccount(fromAccount);
        transactions.setTransactionAmount(200);
        transactions.setTransactionDate(new Date());
        transactions.setTransactionType("credit");
        transactions.setDescription("test");
        listOfTransaction.add(transactions);
        fromAccount.setTransactions(listOfTransaction);

        toAccount = new Account();
        toAccount.setAccountNumber(222L);
        toAccount.setBalance(500);
        List<Transaction> listOfTransaction2= new ArrayList<>();
        Transaction transactions1 = new Transaction();
        transactions.setAccount(fromAccount);
        transactions.setTransactionAmount(200);
        transactions.setTransactionDate(new Date());
        transactions.setTransactionType("credit");
        transactions.setDescription("test");
        listOfTransaction2.add(transactions1);
        toAccount.setTransactions(listOfTransaction2);
    }

    @Test
    void transferFunds_SufficientBalance_SuccessfulTransaction() throws Exception {
        when(transactionRepository.findByaccountNumber(fromAccount.getAccountNumber()))
                .thenReturn(Optional.of(fromAccount));
        when(transactionRepository.findByaccountNumber(toAccount.getAccountNumber()))
                .thenReturn(Optional.of(toAccount));
        when(transactionRepository.save(fromAccount)).thenReturn(fromAccount);
        when(transactionRepository.save(toAccount)).thenReturn(toAccount);
        Account updatedSenderAccount = transactionService.transferFunds(
                fromAccount.getAccountNumber(), toAccount.getAccountNumber(), 500);
        Assertions.assertEquals(500, updatedSenderAccount.getBalance());
        Assertions.assertEquals(1000, toAccount.getBalance());
        verify(transactionRepository, times(2)).save(any(Account.class));
    }

    @Test
    void transferFunds_InsufficientBalance_ThrowsException() {
        int amount = 1500;
        when(transactionRepository.findByaccountNumber(fromAccount.getAccountNumber())).thenReturn(Optional.of(fromAccount));
        when(transactionRepository.findByaccountNumber(toAccount.getAccountNumber())).thenReturn(Optional.of(toAccount));

        Assertions.assertThrows(Exception.class,
                () -> transactionService.transferFunds(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount));
    }

    @Test
    void transferFunds_AccountNotFound_ThrowsException() {
        int amount = 500;
        when(transactionRepository.findById(fromAccount.getAccountNumber())).thenReturn(Optional.of(fromAccount));
        when(transactionRepository.findById(toAccount.getAccountNumber())).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class,
                () -> transactionService.transferFunds(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount));
    }

}
