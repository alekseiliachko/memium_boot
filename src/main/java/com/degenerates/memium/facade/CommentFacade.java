package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class CommentFacade {

    @Autowired
    CommentService commentService;

    @Autowired
    AccountService accountService;

    @Autowired
    ArticleService articleService;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> getCommentsForArticle(UUID articleId) {
        return ResponseEntity.ok(commentService.getByArticleId(articleId));
    }

    public ResponseEntity<?> createComment(CommentDto commentDto, String token) {

        if (commentDto == null || token == null) {
            return ResponseEntity.badRequest().body("missing comment");
        }

        UUID articleId = commentDto.getArticleId();

        if (articleId == null) {
            return ResponseEntity.badRequest().body("missing article");
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("No such author");
        }

        Article article = articleService.getById(articleId);

        if (!account.getAccountId().equals(article.getAuthorId())) {
            return ResponseEntity.badRequest().body("Bad author");
        }

        commentDto.setId(UUID.randomUUID());
        commentDto.setDate(new Date());

        return ResponseEntity.ok(commentService.save(commentDto.toComment()).toCommentDto());
    }

    public ResponseEntity<?> deleteComment(UUID commentId, String token) {

        Comment comment = commentService.getById(commentId);

        if (comment == null) {
            return ResponseEntity.badRequest().body("no such comment");
        }

        Article article = articleService.getById(comment.getArticleId());

        if (comment == null) {
            return ResponseEntity.badRequest().body("no such article");
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (!comment.getAuthorId().equals(account.getAccountId()) && !article.getAuthorId().equals(account.getAccountId())) {
            return ResponseEntity.badRequest().body("not owner of neither article nor comment");
        }

        commentService.deleteById(commentId);
        return ResponseEntity.accepted().body("deleted");
    }
}
