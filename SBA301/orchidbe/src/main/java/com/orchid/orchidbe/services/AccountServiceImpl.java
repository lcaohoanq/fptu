package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.pojos.Role;
import com.orchid.orchidbe.repositories.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(int id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Override
    public void add(AccountDTO.AccountReq account) {

        if (accountRepository.existsByEmail(account.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        var newAccount = new Account();
        newAccount.setName(account.name());
        newAccount.setEmail(account.email());
        newAccount.setPassword(account.password());
        newAccount.setRole(new Role("User")); // Default role, can be changed later

        accountRepository.save(newAccount);
    }

    @Override
    public void update(int id, AccountDTO.AccountReq account) {

        var existingAccount = accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (accountRepository.existsByEmailAndIdNot(account.email(), id)) {
            throw new IllegalArgumentException("Email already exists");
        }

        existingAccount.setName(account.name());
        existingAccount.setEmail(account.email());
        existingAccount.setPassword(account.password());
        existingAccount.setRole(new Role(account.role().name()));

        accountRepository.save(existingAccount);
    }

    @Override
    public void delete(int id) {
        var existingAccount = getById(id);
        accountRepository.delete(existingAccount);
    }
}
