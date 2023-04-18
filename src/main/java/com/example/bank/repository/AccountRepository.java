package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Retuen Account details for the given account details
     * @param id
     * @return
     */
    Optional<Account> findByaccountNumber(Long id);
}
