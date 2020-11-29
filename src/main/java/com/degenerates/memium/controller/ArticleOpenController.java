package com.degenerates.memium.controller;

import com.degenerates.memium.facade.ArticleFacade;
import com.degenerates.memium.model.dto.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/article/")
public class ArticleOpenController {

    @Autowired
    ArticleFacade articleFacade;

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable UUID articleId) {
        return articleFacade.getArticle(articleId);
    }
}
