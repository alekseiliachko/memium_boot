package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.enums.Category;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ArticleShortDto {
    UUID id;

    String imageUrl;

    UUID authorId;

    String title;

    String description;

    Date date;

    Category category;
}
