package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.model.enums.Category;
import com.degenerates.memium.model.dto.ArticleDto;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @NonNull
    UUID articleId;

    String imageUrl;

    @NonNull
    String description;

    @NonNull
    UUID authorId;

    @NonNull
    String title;

    @NonNull
    Date date;

    @NonNull
    Category category;

    @NonNull
    byte[] data;

    public ArticleDto toArticleDto() {
        ArticleDto articleDto = new ArticleDto();

        articleDto.setId(articleId);
        articleDto.setTitle(title);
        articleDto.setAuthorId(authorId);
        articleDto.setCategory(category);
        articleDto.setDescription(description);
        articleDto.setDate(date);
        articleDto.setData(data);

        return articleDto;
    }

    public ArticleShortDto toArticleShortDto() {
        ArticleShortDto articleDto = new ArticleShortDto();

        articleDto.setId(articleId);
        articleDto.setTitle(title);
        articleDto.setAuthorId(authorId);
        articleDto.setCategory(category);
        articleDto.setDescription(description);
        articleDto.setDate(date);

        return articleDto;
    }
}
