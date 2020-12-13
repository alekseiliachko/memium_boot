package com.degenerates.memium.facade;

import com.degenerates.memium.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class LikeFacade {

    @Autowired
    LikeService likeService;

    public ResponseEntity<Long> getLikeAmountForArticle(UUID articleId) {

        Long count = likeService.getLikeCountForArticle(articleId);
        log.info("Found " + count + " likes for article with Id: " + articleId);
        return ResponseEntity.ok(count);
    }
}
