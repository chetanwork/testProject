package com.example.bank;

import com.example.bank.controller.TransactionController;
import com.example.bank.model.Account;
import com.example.bank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAccountDetails_PositiveCase_Returns200() throws Exception {
        Long fromAccount = 1234567890L;
        Long toAccount = 9876543210L;
        int amount = 100;
        Account fromAcc = new Account();
        fromAcc.setBalance(500);
        fromAcc.setAccountNumber(fromAccount);
        fromAcc.setName("John Doe");
        Account toAcc = new Account();
        toAcc.setBalance(1000);
        toAcc.setAccountNumber(toAccount);
        toAcc.setName("Jane Smith");
        when(transactionService.transferFunds(fromAccount, toAccount, amount)).thenReturn(toAcc);
        ResponseEntity<Object> response = transactionController.getAccountDetails(fromAccount, toAccount, amount);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction is success, current Balance is 1000", response.getBody());
    }

    @Test
    public void transferFunds_InsufficientBalance_ReturnsBadRequest() throws Exception {
        Long fromAccount = 1L;
        Long toAccount = 2L;
        int amount = 5000;
        when(transactionService.transferFunds(fromAccount, toAccount, amount))
                .thenThrow(new Exception("Insufficient balance in account " + fromAccount));
        ResponseEntity<Object> response = transactionController.getAccountDetails(fromAccount, toAccount, amount);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient balance in account " + fromAccount, response.getBody());
    }

}
