package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.enums.Category;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ArticleShortDto {
    UUID id;

    UUID authorId;

    String title;

    Date date;

    Category category;
}
