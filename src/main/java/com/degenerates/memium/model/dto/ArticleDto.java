package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.enums.Category;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ArticleDto {

    UUID id;

    String imageUrl;

    String description;

    UUID authorId;

    String title;

    Date date;

    Category category;

    String data;

    public Article toArticle() {
        Article article = new Article();

        article.setArticleId(id);
        article.setAuthorId(authorId);
        article.setTitle(title);
        article.setDescription(description);
        article.setDate(date);
        article.setCategory(category);
        article.setData(data);

        return article;
    }

}
