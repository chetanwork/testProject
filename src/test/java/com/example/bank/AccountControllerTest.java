package com.example.bank;

import com.example.bank.controller.AccountController;
import com.example.bank.error.BankError;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAccountDetails_returnsBalance_whenValidAccountNumber() throws Exception {
        Long accountId = 1L;
        int expectedBalance = 1000;
        Optional<Account> account = Optional.of(new Account());
        account.get().setAccountNumber(111L);
        account.get().setEmail("test@test.com");
        account.get().setBalance(1000);
        account.get().setName("Jon");
        when(accountService.getAccountDetails(accountId)).thenReturn(account);
        ResponseEntity<Object> response = accountController.getAccountDetails(accountId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());
    }

   @Test
    void getAccountDetails_returnsErrorMessage_whenInvalidAccountNumber() throws Exception {
        Long accountId = 1L;
        String expectedErrorMessage = "Please check account Number " + accountId + ", " ;
        when(accountService.getAccountDetails(anyLong())).thenThrow(new Exception(expectedErrorMessage));
        ResponseEntity<Object> response = accountController.getAccountDetails(accountId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedErrorMessage + BankError.ACCOUNT_NOT_FOUND, response.getBody());
    }

    @Test
    void getMiniStatement_returnsTransactions_whenValidAccountNumber() throws Exception {
        Long accountId = 12345L;
        Optional<Account> account = Optional.of(new Account());
        account.get().setAccountNumber(111L);
        account.get().setEmail("test@test.com");
        account.get().setBalance(1000);
        account.get().setName("Jon");
        List<Transaction> listOfTransaction= new ArrayList<>();
        Transaction transactions = new Transaction();
        transactions.setAccount(account.get());
        transactions.setTransactionAmount(200);
        transactions.setTransactionDate(new Date());
        transactions.setTransactionType("credit");
        transactions.setDescription("test");
        Transaction transactions2 = new Transaction();
        transactions2.setAccount(account.get());
        transactions2.setTransactionAmount(400);
        transactions2.setTransactionDate(new Date());
        transactions2.setTransactionType("debit");
        transactions2.setDescription("test1");
        listOfTransaction.add(transactions);
        listOfTransaction.add(transactions2);
        account.get().setTransactions(listOfTransaction);
        when(accountService.getMiniStatement(accountId)).thenReturn(Optional.of(account.get().getTransactions()));
        Optional<List<Transaction>> result = accountService.getMiniStatement(accountId);
        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals(transactions, result.get().get(0));
        assertEquals(transactions2, result.get().get(1));
    }

    @Test
    void getMiniStatement_returnsErrorMessage_whenInvalidAccountNumber() throws Exception {
        Long accountId = 1L;
        String expectedErrorMessage = "Please check account Number " + accountId + ", " ;
        when(accountService.getMiniStatement(anyLong())).thenThrow(new Exception(expectedErrorMessage));
        ResponseEntity<Object> response = accountController.getMiniStatement(accountId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedErrorMessage + BankError.ACCOUNT_NOT_FOUND, response.getBody());
    }
}