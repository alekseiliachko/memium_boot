package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.repository.AccountDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountDetailsService {

    @Autowired
    AccountDetailsRepository accountDetailsRepository;

    public AccountDetails save(AccountDetails accountDetails) {
        return accountDetailsRepository.save(accountDetails);
    }

    public AccountDetails getByAccountId(UUID id) {
        AccountDetails accountDetails =
                accountDetailsRepository
                        .findByAccountIdIn(id)
                        .orElseThrow(EntityNotFoundException::new);
        return accountDetails;
    };

    public List<AccountDetails> getByAccountIds(List<UUID> accountIds) {
        return accountDetailsRepository.findByAccountIdIn(accountIds);
    }

    public void deleteById(UUID id) {
        accountDetailsRepository.deleteById(id);
    }

    }
