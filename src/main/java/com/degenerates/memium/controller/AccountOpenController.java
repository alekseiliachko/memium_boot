package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AccountFacade;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/account/")
public class AccountOpenController {

    @Autowired
    AccountFacade accountFacade;

    @ApiOperation(value = "Get Account by AccountId provided ", response = AccountDto.class, produces = "application/json")
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
    public ResponseEntity<AccountDto> getAccount(@PathVariable UUID accountId) {
        return accountFacade.getAccountById(accountId);
    }

    @ApiOperation(value = "Get Avatar by AccountId provided ", response = AccountDto.class, produces = "application/application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/avatar/{accountId}")
    public byte[] getAccountAvatar(@PathVariable UUID accountId) {
        return accountFacade.getAvatar(accountId);
    }

    @ApiOperation(value = "Get AccountDetails by AccountId provided ", response = AccountDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/details/{accountId}")
    public ResponseEntity<AccountDetailsDto> getAccountDetails(@PathVariable UUID accountId) {
        return accountFacade.getAccountDetailsById(accountId);
    }
}
