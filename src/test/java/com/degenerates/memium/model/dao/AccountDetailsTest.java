package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountShortDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class AccountDetailsTest {

    @Test
    void toFrom() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountId(UUID.randomUUID());
        accountDetails.setGender("gender");

        AccountDetailsDto accountDetailsDto = accountDetails.toAccountDetailsDto();

        assertEquals(accountDetailsDto.toAccountDetails(), accountDetails);
    }

}