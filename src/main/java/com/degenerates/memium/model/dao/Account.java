package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.AccountDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "users")
public class Account {

    @Id
    @NonNull
    UUID accountId;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String password;

    @NonNull
    Date created;

    public AccountDto toAccountDto() {
        AccountDto accountDto = new AccountDto();

        accountDto.setId(getAccountId());
        accountDto.setUsername(getUsername());
        accountDto.setEmail(getEmail());
        accountDto.setCreated(getCreated());

        return accountDto;
    }
}
