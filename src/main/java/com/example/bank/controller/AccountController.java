package com.example.bank.controller;

import com.example.bank.error.BankError;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value="/accounts", produces= MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    /**
     * This is endpoint to return balance of the given account number
     * @param id
     * @return
     * @throws Exception Invalid account number
     */
    @GetMapping("/{id}/balance")
    @ResponseBody
    public ResponseEntity<Object> getAccountDetails(@PathVariable("id") Long id)  throws Exception {
        try {
            Optional<Account> balance = accountService.getAccountDetails(id);
            return new ResponseEntity<>(balance.get().getBalance(), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage() + BankError.ACCOUNT_NOT_FOUND, HttpStatus.OK);
        }
    }

    /**
     * This is endpoint to provide a mini-statement of last 20 transactions
     * @param id
     * @return
     * @throws Exception Invalid account Number
     */
    @GetMapping("/{id}/statements/mini")
    @ResponseBody
    public ResponseEntity<Object> getMiniStatement(@PathVariable("id") Long id) throws Exception{
        try {
            Optional<List<Transaction>> miniState = accountService.getMiniStatement(id);
            return new ResponseEntity<>(miniState, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage() + BankError.ACCOUNT_NOT_FOUND, HttpStatus.OK);
        }
    }
}