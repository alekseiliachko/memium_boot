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

    String name;

    String bio;

    String gender;

    Date dob;

    public AccountDetailsDto toAccountDetailsDto() {
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        accountDetailsDto.setAccountId(getAccountId());
        accountDetailsDto.setName(getName());
        accountDetailsDto.setBio(getBio());
        accountDetailsDto.setGender(getBio());
        accountDetailsDto.setDob(getDob());

        return accountDetailsDto;
    }
}
