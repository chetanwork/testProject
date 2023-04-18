package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Account, Long>{
    /**
     * Find the account by given account number
     * @param id
     * @return
     */
    Optional<Account> findByaccountNumber(Long id);

    /**
     * Save the balance and transaction entry after transfer is done
     * @param entity
     * @return
     */
    Account save(Account entity);
}
