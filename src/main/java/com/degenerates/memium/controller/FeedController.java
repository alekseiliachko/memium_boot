package com.degenerates.memium.controller;

import com.degenerates.memium.facade.FeedFacade;
import com.degenerates.memium.model.dto.QueryDto;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedController {

    @Autowired
    FeedFacade feedFacade;

    @Autowired
    Utils utils;

    @GetMapping
    public ResponseEntity<QueryDto> getFeed(@RequestHeader HttpHeaders headers) {
        return feedFacade.getFeedForUserToken(utils.extractToken(headers));
    }
}
