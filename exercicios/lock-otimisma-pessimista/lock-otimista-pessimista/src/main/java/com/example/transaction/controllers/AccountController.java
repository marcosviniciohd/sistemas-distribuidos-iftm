package com.example.transaction.controllers;

import com.example.transaction.models.Account;
import com.example.transaction.models.TransactionDto;
import com.example.transaction.services.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{id}")
    public Account findById(@PathVariable long id) {
        return accountService.findById(id);
    }

    @PostMapping("/account/transact")
    public void withdraw(@RequestBody TransactionDto<Double> transactionDto) {
        accountService.transact(transactionDto);
    }
}
