package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AccountFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/account/")
public class AccountOpenController {

    @Autowired
    AccountFacade accountFacade;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable UUID accountId) {

        return accountFacade.getAccountById(accountId);
    }

    @GetMapping("/details/{accountId}")
    public ResponseEntity<AccountDetailsDto> getAccountDetails(@PathVariable UUID accountId) {

        return accountFacade.getAccountDetailsById(accountId);
    }
}
