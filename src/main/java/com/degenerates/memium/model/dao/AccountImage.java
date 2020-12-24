package com.degenerates.memium.model.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class AccountImage {

    @Id
    @NonNull
    UUID accountId;

    @NonNull
    byte [] image;
}
