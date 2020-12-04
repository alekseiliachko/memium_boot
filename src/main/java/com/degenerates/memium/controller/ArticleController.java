package com.degenerates.memium.controller;

import com.degenerates.memium.facade.ArticleFacade;
import com.degenerates.memium.model.dto.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/article/")
public class ArticleController {

    @Autowired
    ArticleFacade articleFacade;

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestHeader HttpHeaders headers, @RequestBody ArticleDto articleDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return articleFacade.createArticle(articleDto, token);
    }

    @PutMapping
    public ResponseEntity<ArticleDto> updateArticle(@RequestHeader HttpHeaders headers, @RequestBody ArticleDto articleDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return articleFacade.updateArticle(articleDto, token);
    }

    @DeleteMapping("/{articleId}")
    public HttpStatus deleteArticle(@RequestHeader HttpHeaders headers, @PathVariable UUID articleId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return articleFacade.deleteArticle(articleId, token);
    }
}
