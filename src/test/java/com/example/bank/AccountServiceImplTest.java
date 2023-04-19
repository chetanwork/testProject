package com.example.bank;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.service.AccountService;
import com.example.bank.service.AccountServiceImpl;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAccountDetails_returnsAccount_whenValidAccountNumber() throws Exception {
        Long accountId = 12345L;
        Account account = new Account();
        account.setAccountNumber(accountId);
        account.setEmail("test@test.com");
        account.setBalance(1000);
        account.setName("Jon");
        when(accountRepository.findByaccountNumber(accountId)).thenReturn(Optional.of(account));
        Optional<Account> result = accountService.getAccountDetails(accountId);
        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    public void getMiniStatement_returnsTransactions_whenValidAccountNumber() throws Exception {
        Long accountId = 12345L;
        Account account = new Account();
        account.setAccountNumber(accountId);
        account.setEmail("test@test.com");
        account.setBalance(1000);
        account.setName("Jon");
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setTransactionAmount(100 * i);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(i % 2 == 0 ? "debit" : "credit");
            transaction.setDescription("test " + i);
            transactions.add(transaction);
        }
        account.setTransactions(transactions);
        when(accountRepository.findByaccountNumber(accountId)).thenReturn(Optional.of(account));
        Optional<List<Transaction>> result = accountService.getMiniStatement(accountId);
        assertTrue(result.isPresent());
        assertEquals(20, result.get().size());
        assertEquals(transactions.get(0), result.get().get(19));
        assertEquals(transactions.get(19), result.get().get(0));
    }

    @Test
    void getAccountDetails_throwsException_whenInvalidAccountNumber() {
        Long invalidAccountId = 123L;
        when(accountRepository.findByaccountNumber(invalidAccountId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> accountService.getAccountDetails(invalidAccountId));
        assertEquals("Please check account Number " + invalidAccountId + ", ", exception.getMessage());
    }

    @Test
    void getMiniStatement_throwsException_whenInvalidAccountNumber() throws Exception {
        Long accountId = 12345L;
        when(accountRepository.findByaccountNumber(accountId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> accountService.getMiniStatement(accountId));
        assertEquals("Please check account Number " + accountId + ", ", exception.getMessage());
    }
}
