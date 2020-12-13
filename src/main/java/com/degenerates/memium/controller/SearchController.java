package com.degenerates.memium.controller;

import com.degenerates.memium.facade.FeedFacade;
import com.degenerates.memium.facade.SearchFacade;
import com.degenerates.memium.model.dto.QueryDto;
import com.degenerates.memium.util.Utils;
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

    @GetMapping("/{string}")
    public ResponseEntity<QueryDto> searchAll(@PathVariable String string) {
        return searchFacade.findByString(string);
    }

    @GetMapping("/account/{string}")
    public ResponseEntity<QueryDto> searchAccounts(@PathVariable String string) {
        return searchFacade.findByAccount(string);
    }

    @GetMapping("/article/{string}")
    public ResponseEntity<QueryDto> searchArticles(@PathVariable String string) {
        return searchFacade.findByArticle(string);
    }

    @GetMapping("/category/{string}")
    public ResponseEntity<QueryDto> searchCategories(@PathVariable String string) {
        return searchFacade.findByCategory(string);
    }
}