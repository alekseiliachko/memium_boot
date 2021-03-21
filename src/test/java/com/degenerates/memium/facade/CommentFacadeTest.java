package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.model.dto.CommentSaveDto;
import com.degenerates.memium.model.enums.Category;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.util.Utils;
import lombok.NonNull;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentFacadeTest {
    @Mock
    CommentService commentService;

    @Mock
    ArticleService articleService;

    @Mock
    Utils utils;

    @InjectMocks
    CommentFacade commentFacade;

    private static final UUID accountID_ = UUID.randomUUID();
    private static final UUID articleID_ = UUID.randomUUID();

    private static final String TOKEN = "TOKEN";

    Article getArticle_() {
        Article article = new Article();
        article.setTitle("title");
        article.setDescription("asdsad");
        article.setData("something");
        article.setCategory(Category.Anime);
        article.setArticleId(UUID.randomUUID());
        article.setAuthorId(accountID_);
        article.setDate(new Date());

        return article;
    }
    Account getAccount_() {
        Account account = new Account();
        account.setAccountId(accountID_);
        account.setCreated(new Date());
        account.setEmail("email");
        account.setUsername("username");
        account.setPassword("asdasdsd");

        return account;
    }

    Comment getComment_() {
        Comment comment = new Comment();
        comment.setCommendId(UUID.randomUUID());
        comment.setAuthorId(accountID_);
        comment.setArticleId(articleID_);
        comment.setDate(new Date());
        comment.setContent("content");

        return comment;
    }

    @Test
    void getCommentsForArticle() {

        when(commentService.getByArticleId(articleID_)).thenReturn(Lists.list(getComment_()));

        assertNotEquals(getComment_().toCommentDto(), commentFacade.getCommentsForArticle(articleID_).getBody().get(0));
    }

    @Test
    @Disabled
    void createComment() {

        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);
        CommentSaveDto commentSaveDto = new CommentSaveDto();
        commentSaveDto.setArticleId(articleID_);
        commentSaveDto.setContent("cpmf");

        Comment comment = getComment_();

        when(commentService.save(commentSaveDto.toComment())).thenReturn(comment);

        assertEquals(comment.toCommentDto(), commentFacade.createComment((commentSaveDto),TOKEN).getBody());
    }

    @Test
    void deleteComment() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);
        when(commentService.getById(any())).thenReturn(getComment_());
        when(articleService.getById(any())).thenReturn(getArticle_());

        assertDoesNotThrow(() -> commentFacade.deleteComment(getComment_().getCommendId(), TOKEN));
    }
}
