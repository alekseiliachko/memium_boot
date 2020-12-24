package com.degenerates.memium.util;

import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.exceptions.BadTokenException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@Component
public class Utils {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountService accountService;

    public static AccountShortDto toAccountShortDto(Account account, AccountDetails accountDetails, AccountImage accountImage) {
        AccountShortDto accountShortDto = new AccountShortDto();
        accountShortDto.setAccountId(account.getAccountId());
        accountShortDto.setImageData(accountImage.getImage());
        accountShortDto.setName(accountDetails.getName());
        accountShortDto.setUsername(account.getUsername());
        accountShortDto.setBio(accountDetails.getBio());
        return accountShortDto;
    }

    public Account validateTokenAndGetOwner(String token) {

        Account account;

        try {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            account = accountService.getByUsername(username);
        } catch (RuntimeException e) {
            throw new BadTokenException();
        }

        return account;
    }

    public void accountOwnsItem(Account account, UUID itemOwnerId) {
        if (!itemOwnerId.equals(account.getAccountId())) {
            throw new AccessMismatchException();
        }
    }

    public String extractToken(HttpHeaders headers) {
        String tokenBearer = headers.get(TOKEN_VAR).get(0);
        String token = tokenBearer.substring(7, tokenBearer.length());

        return token;
    }
}
