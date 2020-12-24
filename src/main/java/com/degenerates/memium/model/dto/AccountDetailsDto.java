package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.dao.AccountDetails;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AccountDetailsDto {

    UUID accountId;

    String name;

    String bio;

    String gender;

    Date dob;

    public AccountDetails toAccountDetails() {
        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setAccountId(getAccountId());
        accountDetails.setName(getName());
        accountDetails.setBio(getBio());
        accountDetails.setGender(getGender());
        accountDetails.setDob(getDob());

        return accountDetails;
    }
}
