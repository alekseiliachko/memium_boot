package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.dao.Account;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AccountDto {
    UUID id;

    String username;

    String email;

    Date created;

    UUID accountDetailsId;

    public Account toAccount() {
        Account account = new Account();



        return account;
    };
}
