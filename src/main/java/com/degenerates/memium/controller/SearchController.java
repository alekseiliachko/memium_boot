package com.degenerates.memium.controller;

import com.degenerates.memium.facade.FeedFacade;
import com.degenerates.memium.facade.SearchFacade;
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
@RequestMapping("/api/search/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SearchController {

    @Autowired
    SearchFacade searchFacade;

    @Autowired
    Utils utils;

    @ApiOperation(value = "Search all for string provided ", response = QueryDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/{string}")
    public ResponseEntity<QueryDto> searchAll(@PathVariable String string) {
        return searchFacade.findByString(string);
    }

    @ApiOperation(value = "Search accounts for string provided ", response = QueryDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/account/{string}")
    public ResponseEntity<QueryDto> searchAccounts(@PathVariable String string) {
        return searchFacade.findByAccount(string);
    }

    @ApiOperation(value = "Search articles for string provided ", response = QueryDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/article/{string}")
    public ResponseEntity<QueryDto> searchArticles(@PathVariable String string) {
        return searchFacade.findByArticle(string);
    }

    @ApiOperation(value = "Search categories for string provided ", response = QueryDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/category/{string}")
    public ResponseEntity<QueryDto> searchCategories(@PathVariable String string) {
        return searchFacade.findByCategory(string);
    }
}