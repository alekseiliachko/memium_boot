package com.degenerates.memium.facade;

import com.degenerates.memium.config.PasswordHasher;
import com.degenerates.memium.config.TokenCreator;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dto.AccountDto;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.service.AccountDetailsService;
import com.degenerates.memium.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

public class LogFacade {

    @Autowired
    PasswordHasher passwordHasher;

    @Autowired
    TokenCreator tokenCreator;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailsService accountDetailsService;

    String logUserIn() {

        return "In";
    }

    String signUserUp(SignupForm signupForm) {

        UUID id = UUID.randomUUID();

        Account account = new Account();
        AccountDetails accountDetails = new AccountDetails();

        account.setAccountId(id);
        accountDetails.setAccountId(id);

        account.setUsername(signupForm.getUsername());
        account.setPassword(passwordHasher.hash(signupForm.getPassword()));
        account.setEmail(signupForm.getEmail());
        account.setCreated(new Date());

        accountDetails.setAccountId(id);
        accountDetails.setName(signupForm.getName());
        accountDetails.setDob(signupForm.getDob());
        accountDetails.setGender(signupForm.getGender());

        Account accountSaved = accountService.save(account);
        AccountDetails accountDetailsSaved = accountDetailsService.save(accountDetails);

        if (accountSaved != null && accountDetailsSaved != null)
            return tokenCreator.createToken(account.getUsername());
        else
            return null;
    }
}
