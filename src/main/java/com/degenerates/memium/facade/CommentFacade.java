package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.model.dto.CommentSaveDto;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommentFacade {

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleService articleService;

    @Autowired
    Utils utils;

    public ResponseEntity<List<CommentDto>> getCommentsForArticle(UUID articleId) {

        List<Comment> comments = commentService.getByArticleId(articleId);
        log.info("Found " + comments.size() + " comments for article with Id: " + articleId);

        return ResponseEntity.ok(comments.stream().map(Comment::toCommentDto).collect(Collectors.toList()));
    }

    public ResponseEntity<CommentDto> createComment(CommentSaveDto commentSaveDto, String token) {

        Account account  = utils.validateTokenAndGetOwner(token);

        Comment comment = commentSaveDto.toComment();
        comment.setCommendId(UUID.randomUUID());
        comment.setAuthorId(account.getAccountId());
        comment.setDate(new Date());

        comment = commentService.save(comment);

        log.info("Saved comment with Id: " + comment.getCommendId());

        return ResponseEntity.ok(comment.toCommentDto());
    }

    public HttpStatus deleteComment(UUID commentId, String token) {

        Comment comment = commentService.getById(commentId);
        Article article = articleService.getById(comment.getArticleId());
        Account account = utils.validateTokenAndGetOwner(token);

        if (!comment.getAuthorId().equals(account.getAccountId()) && !article.getAuthorId().equals(account.getAccountId())) {
            throw new AccessMismatchException();
        }

        commentService.deleteById(commentId);
        log.info("Deleted comment with Id: " + commentId);

        return HttpStatus.OK;
    }
}
