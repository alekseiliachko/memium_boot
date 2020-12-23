package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.enums.Category;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ArticleSaveDto {

    String title;

    String description;

    String imageUrl;

    Category category;

    String data;

    public Article toArticle() {
        Article article = new Article();

        article.setTitle(title);
        article.setDescription(description);
        article.setImageUrl(imageUrl);
        article.setCategory(category);
        article.setData(data);

        return article;
    }
}
