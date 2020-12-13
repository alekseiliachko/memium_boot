package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.CorruptedFileException;
import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.ArticleImage;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.ArticleSaveDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.service.ArticleImageService;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.service.LikeService;
import com.degenerates.memium.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ArticleFacade {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleImageService articleImageService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    Utils utils;

    public ResponseEntity<ArticleDto> getArticle(UUID articleId) {
        Article article = articleService.getById(articleId);
        log.info("Found article by Id: " + articleId);
        return ResponseEntity.ok(article.toArticleDto());
    }

    public ResponseEntity<List<ArticleShortDto>> getMyArticles(String token) {
        Account account = utils.validateTokenAndGetOwner(token);
        List<Article> articles = articleService.getByAccountId(account.getAccountId());

        log.info("Found " + articles.size() + " articles for account with Id: " + account.getAccountId());
        return ResponseEntity.ok(articles.stream().map(Article::toArticleShortDto).collect(Collectors.toList()));
    }


    public ResponseEntity<List<ArticleShortDto>> getArticlesForAccountId(UUID accountId) {
        List<Article> articles = articleService.getByAccountId(accountId);
        log.info("Found " + articles.size() + " articles from account with Id: " + accountId);
        return ResponseEntity.ok(articles.stream().map(Article::toArticleShortDto).collect(Collectors.toList()));
    }

    public ResponseEntity<ArticleDto> createArticle(ArticleSaveDto articleSaveDto, String token) {

        Account account = utils.validateTokenAndGetOwner(token);

        Article article = articleSaveDto.toArticle();
        article.setArticleId(UUID.randomUUID());
        article.setAuthorId(account.getAccountId());
        article.setDate(new Date());

        article = articleService.save(article);
        log.info("Created new article with Id: " + article.getArticleId());

        return ResponseEntity.ok(article.toArticleDto());
    }

    public byte[] getArticleImage(UUID articleId) {
        ArticleImage articleImage = articleImageService.getByArticleId(articleId);
        log.info("Found articleImage for article with Id: " + articleId);

        return articleImage.getImage();
    }

    public ResponseEntity<UUID> setArticleImage(String token, UUID articleId, MultipartFile imageFile) {

        Account account = utils.validateTokenAndGetOwner(token);
        ArticleImage articleImage = new ArticleImage();

        if (!articleService.checkIfExists(articleId)) {
            throw new EntityNotFoundException();
        }

        articleImage.setArticleId(articleId);

        try {
            articleImage.setImage(imageFile.getBytes());
        } catch (IOException e) {
            throw new CorruptedFileException();
        }

        if (articleImageService.checkIfAlreadySet(articleId)) {
            articleImageService.deleteByArticleId(articleId);
            log.info("ArticleImage already set, deleted: " + articleId);
        }

        articleImage = articleImageService.save(articleImage);
        log.info("ArticleImage image set: " + articleId);

        return ResponseEntity.ok(articleImage.getArticleId());
    }

    public ResponseEntity<ArticleDto> updateArticle(ArticleDto articleDto, String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        utils.accountOwnsItem(account, articleDto.getAuthorId());

        Article article = articleService.getById(articleDto.getId());

        article.setTitle(articleDto.getTitle());
        article.setCategory(articleDto.getCategory());
        article.setDate(new Date());

        article = articleService.save(article);
        log.info("Article updated with id: " + article.getArticleId());

        return ResponseEntity.ok(article.toArticleDto());
    }

    public HttpStatus deleteArticle(UUID articleId, String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        utils.accountOwnsItem(account, articleService.getById(articleId).getAuthorId());

        articleService.deleteById(articleId);
        commentService.deleteByAtricleId(articleId);
        likeService.unlikeAllByAticleId(articleId);

        log.info("Article deleted with id: " + articleId);

        return HttpStatus.OK;
        
    }

    public ResponseEntity<Boolean> checkIfAccountLikedArticle(UUID articleId, String token) {
        Account account = utils.validateTokenAndGetOwner(token);
        Boolean flag = likeService.checkIfAccountLikedArticle(account.getAccountId(), articleId);
        log.info("Account " + account.getUsername() +" like on " + articleId + " is: " + flag.toString());
        return ResponseEntity.ok(flag);
    }
}
