package com.degenerates.memium.controller;

import com.degenerates.memium.facade.CommentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/comment")
public class CommentOpenController {

    @Autowired
    CommentFacade commentFacade;

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getCommentsForArticle(@PathVariable UUID articleId) {
        return commentFacade.getCommentsForArticle(articleId);
    }
}
