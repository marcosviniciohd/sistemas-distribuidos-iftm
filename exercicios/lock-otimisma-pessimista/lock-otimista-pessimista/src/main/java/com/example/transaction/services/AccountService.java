package com.example.transaction.services;


import com.example.transaction.models.Account;
import com.example.transaction.models.TransactionDto;
import com.example.transaction.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Transactional()
    public void transact(TransactionDto<Double> transactionDto) {
        Account account = accountRepository.findWithLockingById(transactionDto.id());
        double newBalance = account.getBalance() + transactionDto.value();
        if (newBalance < 0) throw new RuntimeException("Insufficient funds");
        account.setBalance(newBalance);
    }
}
