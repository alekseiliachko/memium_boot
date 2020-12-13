package com.degenerates.memium.service;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountShortService {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailsService accountDetailsService;

    @Autowired
    AccountImageService accountImageService;

    public AccountShortDto getAccountById(UUID accountId) {
        Account account = accountService.getById(accountId);
        AccountDetails accountDetails = accountDetailsService.getByAccountId(accountId);
        AccountImage accountImage = accountImageService.getByAccountId(accountId);

        return Utils.toAccountShortDto(account, accountDetails, accountImage);
    }

    public List<AccountShortDto> getAccountsByIds(List<UUID> list) {
        List<AccountShortDto> accountShortDtoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Account account = accountService.getById(list.get(i));
            AccountDetails accountDetails = accountDetailsService.getByAccountId(list.get(i));
            AccountImage accountImage = accountImageService.getByAccountId(list.get(i));
            accountShortDtoList.add(Utils.toAccountShortDto(account, accountDetails, accountImage));
        }
        return accountShortDtoList;
    }
}
