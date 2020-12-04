package com.degenerates.memium.controller;

import com.degenerates.memium.facade.SubFacade;
import com.degenerates.memium.model.dto.AccountShortDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/sub")
public class SubOpenController {

        @Autowired
        SubFacade subFacade;

        @GetMapping("/{accountId}")
        public ResponseEntity<List<AccountShortDto>> getSubsForArticle(@PathVariable UUID accountId) {
            return subFacade.getSubOfAccount(accountId);
        }
    }
