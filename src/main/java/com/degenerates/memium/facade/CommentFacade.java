package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    Validators validators;

    public ResponseEntity<List<CommentDto>> getCommentsForArticle(UUID articleId) {
        return ResponseEntity.ok(commentService.getByArticleId(articleId).stream().map(Comment::toCommentDto).collect(Collectors.toList()));
    }

    public ResponseEntity<CommentDto> createComment(CommentDto commentDto, String token) {

        Account account  = validators.validateTokenAndGetOwner(token);
        validators.validateAccountAndItemOwnership(account,commentDto.getAuthorId());

        commentDto.setId(UUID.randomUUID());
        commentDto.setDate(new Date());

        return ResponseEntity.ok(commentService.save(commentDto.toComment()).toCommentDto());
    }

    public HttpStatus deleteComment(UUID commentId, String token) {

        Comment comment = commentService.getById(commentId);

        Article article = articleService.getById(comment.getArticleId());

        Account account = validators.validateTokenAndGetOwner(token);

        if (!comment.getAuthorId().equals(account.getAccountId()) && !article.getAuthorId().equals(account.getAccountId())) {
            throw new AccessMismatchException();
        }

        commentService.deleteById(commentId);
        return HttpStatus.OK;
    }
}
