package com.degenerates.memium.facade;

import com.degenerates.memium.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LikeFacade {

    @Autowired
    LikeService likeService;

    public ResponseEntity<Long> getLikeAmountForArticle(UUID articleId) {

        return ResponseEntity.ok(likeService.getLikeCountForArticle(articleId));
    }
}
