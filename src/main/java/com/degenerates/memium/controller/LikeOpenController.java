package com.degenerates.memium.controller;

import com.degenerates.memium.facade.LikeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/like")
public class LikeOpenController {

    @Autowired
    LikeFacade likeFacade;

    @GetMapping("/{articleId}")
    public ResponseEntity<Integer> getLikeForArticle(@PathVariable UUID articleId) {
        return likeFacade.getLikeAmountForArticle(articleId);
    }
}
