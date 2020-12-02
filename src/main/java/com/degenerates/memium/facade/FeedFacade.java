package com.degenerates.memium.facade;

import com.degenerates.memium.model.dto.FeedDto;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.BlackListService;
import com.degenerates.memium.service.LikeService;
import com.degenerates.memium.service.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FeedFacade {

    @Autowired
    AccountService accountService;

    @Autowired
    SubService subService;

    @Autowired
    BlackListService blackListService;

    @Autowired
    LikeService likeService;

    public ResponseEntity<?> getFeedForUserToken(String token) {
        FeedDto feedDto = new FeedDto();


        return ResponseEntity.ok(feedDto);
    }
}
