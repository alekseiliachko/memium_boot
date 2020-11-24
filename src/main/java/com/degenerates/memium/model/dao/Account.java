package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.AccountDto;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Account {

    @Id
    @NonNull
    UUID id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String password;

    @NonNull
    Date created;

    @NonNull
    UUID accountDetailsId;

    public AccountDto toAccountDto() {
        AccountDto accountDto = new AccountDto();

        return accountDto;
    }
}
