package com.degenerates.memium.controller;

import com.degenerates.memium.facade.ArticleFacade;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/article/")
public class ArticleOpenController {

    @Autowired
    ArticleFacade articleFacade;

    @GetMapping("/accountid/{accountId}")
    public ResponseEntity<List<ArticleShortDto>> getArticlesByAccountId(@PathVariable UUID accountId) {
        return articleFacade.getArticlesForAccountId(accountId);
    }

    @GetMapping("/articleid/{articleId}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable UUID articleId) {
        return articleFacade.getArticle(articleId);
    }
}
