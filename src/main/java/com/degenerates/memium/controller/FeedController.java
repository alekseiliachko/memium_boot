package com.degenerates.memium.controller;

import com.degenerates.memium.facade.FeedFacade;
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

    @GetMapping
    public ResponseEntity<?> getFeed(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return feedFacade.getFeedForUserToken(token);
    }
}
