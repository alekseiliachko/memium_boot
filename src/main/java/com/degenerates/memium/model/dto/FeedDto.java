package com.degenerates.memium.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedDto {
    List<ArticleDto> articles;
    List<AccountShortDto> accounts;
}
