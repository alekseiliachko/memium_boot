package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class ArticleFacade {

    @Autowired
    ArticleService articleService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountService accountService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    public ResponseEntity<?> getArticle(UUID articleId) {
        return ResponseEntity.ok(articleService.getById(articleId).toArticleDto());
    }

    public ResponseEntity<?> createArticle(ArticleDto articleDto, String token) {
        if (articleDto == null || token == null) {
            return ResponseEntity.badRequest().body("missing data");
        }

        if ((articleDto.getId() != null) && (articleService.checkIfExists(articleDto.getId()))) {
            return ResponseEntity.badRequest().body("article already exists");
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("you are no author");
        }

        articleDto.setId(UUID.randomUUID());
        articleDto.setDate(new Date());
        articleDto.setAuthorId(account.getAccountId());
        Article article = articleDto.toArticle();

        return ResponseEntity.ok(articleService.save(article).toArticleDto());
    }

    public ResponseEntity<?> updateArticle(ArticleDto articleDto, String token) {
        if (articleDto == null || token == null) {
            return ResponseEntity.badRequest().body("missing data");
        }

        if (articleDto.getId() == null) {
            return ResponseEntity.badRequest().body("no id");
        }

        Article article = articleService.getById(articleDto.getId());

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null || !article.getAuthorId().equals(account.getAccountId())) {
            return ResponseEntity.badRequest().body("you are no author");
        }

        article.setTitle(articleDto.getTitle());
        article.setCategory(articleDto.getCategory());
        article.setDate(new Date());

        return ResponseEntity.ok(articleService.save(article).toArticleDto());
    }

    public ResponseEntity<?> deleteArticle(UUID articleId, String token) {
        if (articleId == null || token == null) {
            return ResponseEntity.badRequest().body("missing data");
        }

        Article article = articleService.getById(articleId);

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null || !article.getAuthorId().equals(account.getAccountId())) {
            return ResponseEntity.badRequest().body("you are no author");
        }

        articleService.deleteById(articleId);
        commentService.deleteByAtricleId(articleId);
        likeService.unlikeAllByAticleId(articleId);

        return ResponseEntity.accepted().body("deleted");
        
    }
}
