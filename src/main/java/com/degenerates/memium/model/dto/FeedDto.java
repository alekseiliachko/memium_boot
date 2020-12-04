package com.degenerates.memium.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedDto {
    List<ArticleShortDto> articles;
    List<AccountShortDto> accounts;
}
