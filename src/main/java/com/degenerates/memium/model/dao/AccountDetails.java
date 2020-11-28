package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.AccountDetailsDto;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class AccountDetails {

    @Id
    @NonNull
    UUID accountId;

    @NonNull
    String name;

    String bio;

    String gender;

    @NonNull
    Date dob;

    public AccountDetailsDto toAccountDetailsDto() {
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        accountDetailsDto.setAccountId(getAccountId());
        accountDetailsDto.setName(accountDetailsDto.getName());
        accountDetailsDto.setBio(accountDetailsDto.getBio());
        accountDetailsDto.setGender(accountDetailsDto.getBio());
        accountDetailsDto.setDob(accountDetailsDto.getDob());

        return accountDetailsDto;
    }
}
