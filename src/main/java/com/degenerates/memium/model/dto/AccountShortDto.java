package com.degenerates.memium.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountShortDto {
    UUID accountId;
    String username;
    String name;
    String bio;
    byte[] imageData;
}
