package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountDto;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void toFrom() {
        Account account = new Account();
        account.setAccountId(UUID.randomUUID());
        account.setCreated(new Date());
        account.setEmail("email");
        account.setUsername("username");

        AccountDto accountDto = account.convertToAccountDto();

        assertEquals(accountDto.toAccount(), account);
    }
}