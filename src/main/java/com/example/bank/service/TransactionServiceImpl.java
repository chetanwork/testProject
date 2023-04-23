package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    private static String DEBITED = "debited";

    private static String CREDITED = "credited";

    private static String TRANSFER = "transfer";

    /**
     * This is implementation class for performing Transaction from one account to other
     * @param fromAccount - Account from which money need to be transferred
     * @param toAccount - Account to which amount need to be transferred
     * @param amount - amaount need to be transferred fromAccount to toAccount
     * @return Account
     * @throws Exception if Account provided is not found, if balance is less than expected transfer amount
     */
    @Override
    @Transactional(isolation=Isolation.READ_COMMITTED)
    public Account transferFunds(Long fromAccount, Long toAccount, int amount) throws Exception {
        Optional<Account> senderAccount = transactionRepository.findByaccountNumber(fromAccount);
        if(!senderAccount.isPresent()) {
            throw new Exception("Sender account accountNumber "+ fromAccount + " is not found");
        }
        Optional<Account> receiverAccount = transactionRepository.findByaccountNumber(toAccount);
        if(!receiverAccount.isPresent()) {
            throw new Exception("Receiver account accountNumber " + toAccount + "is not found");
        }

        if (senderAccount.get().getBalance() >= amount ) {
            setSenderAccountDetails(senderAccount.get(), amount, receiverAccount.get());
            setReceiverAccountDetails(receiverAccount.get(), amount, senderAccount.get());
            return senderAccount.get();
        } else {
            throw new Exception("Insufficient funds available in account " + senderAccount.get().getAccountNumber());
        }

    }

    /**
     * This method set the balance after Transaction in sender account with Transaction entry
     * @param sendAccount
     * @param amount
     * @param recAccount
     */
    private void setSenderAccountDetails(Account sendAccount, int amount, Account recAccount) {
        sendAccount.setBalance(sendAccount.getBalance() - amount);
        Transaction debAccountEntry = createTransEntry(sendAccount, amount, DEBITED);
        sendAccount.getTransactions().add(debAccountEntry);
        transactionRepository.save(sendAccount);
    }

    /**
     * This method set the balance after Transaction in receiver account with Transaction entry
     * @param recAccount
     * @param amount
     * @param sendAccount
     */
    private void setReceiverAccountDetails(Account recAccount, int amount, Account sendAccount) {
        recAccount.setBalance(recAccount.getBalance() + amount);
        Transaction recAccountEntry = createTransEntry(sendAccount, amount, CREDITED);
        recAccount.getTransactions().add(recAccountEntry);
        transactionRepository.save(recAccount);
    }

    /**
     * This method create an transacation entry which needs to be stored in transaction table
     * @param account
     * @param amount
     * @param type
     * @return Transaction
     */
    @SuppressWarnings("deprecation")
    private Transaction createTransEntry(Account account, int amount, String type) {
        Transaction transactionEntry = new Transaction();
        transactionEntry.setDescription(TRANSFER);
        transactionEntry.setTransactionType(type);
        transactionEntry.setTransactionAmount(amount);
        transactionEntry.setTransactionDate(new Date());
        transactionEntry.setAccount(account);
        return transactionEntry;
    }
}
