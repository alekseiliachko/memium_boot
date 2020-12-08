package com.degenerates.memium.controller;

import com.degenerates.memium.facade.FeedFacade;
import com.degenerates.memium.model.dto.FeedDto;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@RestController
@RequestMapping("/api/feed/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedController {

    @Autowired
    FeedFacade feedFacade;

    @Autowired
    Utils utils;

    @GetMapping
    public ResponseEntity<FeedDto> getFeed(@RequestHeader HttpHeaders headers) {
        return feedFacade.getFeedForUserToken(utils.extractToken(headers));
    }
}
