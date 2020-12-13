package com.degenerates.memium.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QueryDto {
    List<ArticleShortDto> articles;
    List<AccountShortDto> accounts;
}
