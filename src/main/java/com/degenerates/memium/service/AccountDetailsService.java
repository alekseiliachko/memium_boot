package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.repository.AccountDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountDetailsService {


    @Autowired
    AccountDetailsRepository accountDetailsRepository;

    public AccountDetails save(AccountDetails accountDetails) {
        AccountDetails accountDetailsSaved = accountDetailsRepository.save(accountDetails);
        System.out.println(accountDetailsSaved);
        return accountDetails;
    }

    public AccountDetails getByAccountId(UUID id) {
        AccountDetails accountDetails = accountDetailsRepository.findByAccountIdIn(id).orElseThrow(EntityNotFoundException::new);
        return accountDetails;
    };

}
