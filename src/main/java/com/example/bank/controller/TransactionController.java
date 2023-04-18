package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/transaction", produces= MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    /**
     * This is endpoint called for making an transaction.
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @return ResponseEntity
     * @throws Exception Account not valid or insufficent balance
     */
    @GetMapping("/transfer/{from}/{to}/{amount}")
    @ResponseBody
    public ResponseEntity<Object> getAccountDetails(@PathVariable("from") Long fromAccount,
                                                    @PathVariable("to") Long toAccount,
                                                    @PathVariable("amount") int amount) throws Exception {
        try {
            Account transaction = transactionService.transferFunds(fromAccount, toAccount, amount);
            return new ResponseEntity<>("Transaction is success, current Balance is " + transaction.getBalance(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
