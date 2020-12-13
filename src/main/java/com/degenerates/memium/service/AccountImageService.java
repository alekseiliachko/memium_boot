package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.exceptions.OptionalEntityNotFoundException;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.repository.AccountImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountImageService {

    @Autowired
    AccountImageRepository accountImageRepository;

    public AccountImage save(AccountImage accountImage) {
        return accountImageRepository.save(accountImage);
    }

    public boolean checkIfAlreadySet(UUID accountId) {
        return accountImageRepository.existsByAccountId(accountId);
    }

    public AccountImage getByAccountId(UUID accountId) {
        return accountImageRepository.findByAccountId(accountId).orElseThrow(OptionalEntityNotFoundException::new);
    }

    public void deleteByAccountId(UUID accountId) {
        accountImageRepository.deleteByAccountId(accountId);
    }
}
