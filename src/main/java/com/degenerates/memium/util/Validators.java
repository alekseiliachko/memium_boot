package com.degenerates.memium.util;

import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.exceptions.BadTokenException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Validators {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountService accountService;

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
}
