package com.degenerates.memium.controller;

import com.degenerates.memium.facade.SubFacade;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.model.dto.QueryDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get Short Accounts token from Headers is subbed to ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountShortDto>> getSubsForArticle(@PathVariable UUID accountId) {
            return subFacade.getSubOfAccount(accountId);
    }
}
