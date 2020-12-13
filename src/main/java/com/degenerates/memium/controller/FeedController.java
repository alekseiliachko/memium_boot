package com.degenerates.memium.controller;

import com.degenerates.memium.facade.FeedFacade;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.model.dto.QueryDto;
import com.degenerates.memium.util.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get feed for token provided in Headers ", response = QueryDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping
    public ResponseEntity<QueryDto> getFeed(@RequestHeader HttpHeaders headers) {
        return feedFacade.getFeedForUserToken(utils.extractToken(headers));
    }
}
