package com.degenerates.memium.service;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> getByUsernameR(String username) {
        return accountRepository.findByUsernameRegex(username);
    }

    public Account save(Account account) {
        Account accountSaved = accountRepository.save(account);
        log.info("Saved accound: " + account.getAccountId());
        return accountSaved;
    }

    public Account getById(UUID id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null)
            log.info("Found account with id: " + id);
        else
            log.info("No account with id: " + id);
        return account;
    };

    public List<Account> getByIds(List<UUID> ids) {
        return accountRepository.findByAccountId(ids);
    }

    public Boolean checkIfExists(String username) {
        return accountRepository.existsByUsername(username);
    }

    public Account getByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account != null)
            log.info("Found account with username: " + username);
        else
            log.info("No account with username: " + username);
        return account;
    }

    public void deleteById(UUID id) {
        accountRepository.deleteById(id);

        log.info("Deleted account with id: " + id);
    }
}
