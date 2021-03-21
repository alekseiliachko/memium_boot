package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.enums.Category;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    void toFrom() {
        Article article = new Article();
        article.setTitle("title");
        article.setDescription("asdsad");
        article.setData("something");
        article.setCategory(Category.Anime);
        article.setArticleId(UUID.randomUUID());
        article.setAuthorId(UUID.randomUUID());
        article.setDate(new Date());

        ArticleDto articleDto = article.toArticleDto();

        assertEquals(articleDto.toArticle(), article);
    }
}