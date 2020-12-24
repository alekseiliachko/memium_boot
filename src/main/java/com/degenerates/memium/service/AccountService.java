package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
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
        return accountRepository.save(account);
    }

    public Account getById(UUID id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    };

    public Boolean checkIfExists(String username) {
        return accountRepository.existsByUsername(username);
    }

    public Account getByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }
}
