package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.service.LikeService;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ArticleFacade {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    Utils utils;

    public ResponseEntity<ArticleDto> getArticle(UUID articleId) {
        return ResponseEntity.ok(articleService.getById(articleId).toArticleDto());
    }

    public ResponseEntity<List<ArticleShortDto>> getArticlesForAccountId(UUID accountId) {
        return ResponseEntity.ok(articleService.getByAccountId(accountId).stream().map(Article::toArticleShortDto).collect(Collectors.toList()));
    }

    public ResponseEntity<ArticleDto> createArticle(ArticleDto articleDto, String token) {

        Account account = utils.validateTokenAndGetOwner(token);

        articleDto.setId(UUID.randomUUID());
        articleDto.setDate(new Date());
        articleDto.setAuthorId(account.getAccountId());
        Article article = articleDto.toArticle();

        return ResponseEntity.ok(articleService.save(article).toArticleDto());
    }

    public ResponseEntity<ArticleDto> updateArticle(ArticleDto articleDto, String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        utils.accountOwnsItem(account, articleDto.getAuthorId());

        Article article = articleService.getById(articleDto.getId());

        article.setTitle(articleDto.getTitle());
        article.setCategory(articleDto.getCategory());
        article.setDate(new Date());

        return ResponseEntity.ok(articleService.save(article).toArticleDto());
    }

    public HttpStatus deleteArticle(UUID articleId, String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        utils.accountOwnsItem(account, articleService.getById(articleId).getAuthorId());

        articleService.deleteById(articleId);
        commentService.deleteByAtricleId(articleId);
        likeService.unlikeAllByAticleId(articleId);

        return HttpStatus.OK;
        
    }
}
