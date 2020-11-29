package com.degenerates.memium.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountShortDto {
    String name;
    byte[] imageData;
}
