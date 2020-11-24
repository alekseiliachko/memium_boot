package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountDto;
import com.degenerates.memium.service.AccountDetailsService;
import com.degenerates.memium.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class AccountFacade {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailsService accountDetailsService;


    AccountDto saveAccount(AccountDto accountDto) {
        Account account = accountDto.toAccount();

        return accountService.save(account).toAccountDto();
    };

    AccountDto findAccountById(UUID id) {
        Account account = accountService.getById(id);

        return account == null? null: account.toAccountDto();
    }

    AccountDto findAccountByUsername(String username) {
        Account account = accountService.getByUsername(username);

        return account == null? null: account.toAccountDto();
    }

    void deleteAccount(UUID id) {
        this.accountService.deleteById(id);
    }

    // details
    AccountDetailsDto saveAccountDetalils(AccountDetailsDto accountDetailsDto) {
        Account account = accountService.getById(accountDetailsDto.getAccountId());
        AccountDetails accountDetailsToSave = accountDetailsService.getByDetailsId(account.getAccountDetailsId());
        AccountDetails accountDetails = accountDetailsDto.toAccountDetails();

        accountDetailsToSave.setName(accountDetails.getName());
        accountDetailsToSave.setBio(accountDetails.getBio());
        accountDetailsToSave.setDetailsId(accountDetails.getDetailsId());
        accountDetailsToSave.setDob(accountDetails.getDob());
        accountDetailsToSave.setGender(accountDetails.getGender());

        AccountDetailsDto accountDetailsDtoResponse = accountDetailsService.save(accountDetailsToSave).toAccountDetailsDto();
        return accountDetailsDtoResponse;
    };

    AccountDetailsDto findAccountDetailsByAccountId(UUID accountId) {
        Account account = accountService.getById(accountId);
        AccountDetails accountDetails = accountDetailsService.getByDetailsId(account.getAccountDetailsId());
        return accountDetails.toAccountDetailsDto();
    }


}
