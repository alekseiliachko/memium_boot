package com.degenerates.memium.service;

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
        log.info("Saved account details: " + accountDetailsSaved.getDetailsId());
        return accountDetails;
    }

    public AccountDetails getByDetailsId(UUID id) {
        AccountDetails accountDetails = accountDetailsRepository.findByDetailsId(id);
        if (accountDetails != null)
            log.info("Found account details with id: " + id);
        else
            log.info("No account details with id: " + id);
        return accountDetails;
    };

    public void deleteById(UUID id) {
        accountDetailsRepository.deleteById(id);

        log.info("Deleted account details with id: " + id);
    }
}
