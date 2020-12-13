package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AccountFacade;

import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.util.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    AccountFacade accountFacade;

    @Autowired
    Utils utils;

    @ApiOperation(value = "Get Avatar based on current token in Headers ", response = byte[].class, produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/avatar")
    public byte[] getAvatar(@RequestHeader HttpHeaders headers) {
        return accountFacade.getAvatar(utils.extractToken(headers));
    }

    @ApiOperation(value = "Set Avatar based on current token in Headers ", response = byte[].class, produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping("/avatar")
    public byte[] setAvatar(@RequestHeader HttpHeaders headers, @RequestParam("image") MultipartFile image) {
        return accountFacade.setAvatar(utils.extractToken(headers), image);
    }

    @ApiOperation(value = "Delete Avatar based on current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @DeleteMapping("/avatar")
    public HttpStatus deleteCurrentAvatar(@RequestHeader HttpHeaders headers) {
        return accountFacade.deleteCurrentAvatar(utils.extractToken(headers));
    }





    //                          ACCOUNT
    @ApiOperation(value = "Get Account based on current token in Headers ", response = AccountDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping
    public ResponseEntity<AccountDto> getAccount(@RequestHeader HttpHeaders headers) {
        return accountFacade.getAccount(utils.extractToken(headers));
    }





    //                          EMAIL, DETAILS
    @ApiOperation(value = "Get AccountDetails based on current token in Headers ", response = AccountDetailsDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/details")
    public ResponseEntity<AccountDetailsDto> getDetails(@RequestHeader HttpHeaders headers) {
        return accountFacade.getAccountDetails(utils.extractToken(headers));
    }

    @ApiOperation(value = "Update AccountDetails based on current token in Headers ", response = AccountDetailsDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PutMapping("/details")
    public ResponseEntity<AccountDetailsDto> updateDetails(@RequestHeader HttpHeaders headers, @RequestBody AccountDetailsDto accountDetailsDto) {
        return accountFacade.updateAccountDetails(utils.extractToken(headers), accountDetailsDto);
    }

    @ApiOperation(value = "Update Account Email based on current token in Headers ", response = AccountDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PutMapping("/email")
    public ResponseEntity<AccountDto> updateAccount(@RequestHeader HttpHeaders headers, UpdatePasswordEmailDto updatePasswordEmailDto) {
        return accountFacade.updateAccount(utils.extractToken(headers), updatePasswordEmailDto);
    }




    //                          SUBSCRIBE
    @ApiOperation(value = "Get subs as Short Accounts based on current token in Headers ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/sub")
    public ResponseEntity<List<AccountShortDto>> getSub(@RequestHeader HttpHeaders headers) {
        return accountFacade.getSubscriptions(utils.extractToken(headers));
    }

    @ApiOperation(value = "Sub to accountId provided by current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping("/sub")
    public HttpStatus subscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.subscribe(utils.extractToken(headers), accountId);
    }

    @ApiOperation(value = "UnSub from accountId provided by current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @DeleteMapping("/sub")
    public HttpStatus unSubscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.unsubscribe(utils.extractToken(headers), accountId);
    }




    //                          LIKES
    @ApiOperation(value = "Get likes as Short Articles based on current token in Headers ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/like")
    public ResponseEntity<List<ArticleShortDto>> getLikes(@RequestHeader HttpHeaders headers) {
        return accountFacade.getLikes(utils.extractToken(headers));
    }

    @ApiOperation(value = "Like articleId provided by current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping("/like")
    public HttpStatus like(@RequestHeader HttpHeaders headers, UUID articleId) {
        return accountFacade.like(utils.extractToken(headers), articleId);
    }

    @ApiOperation(value = "UnLike articleId provided by current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @DeleteMapping("/like")
    public HttpStatus unLike(@RequestHeader HttpHeaders headers, UUID articleId) {
        return accountFacade.unlike(utils.extractToken(headers), articleId);
    }






    //                          BLACKLIST
    @ApiOperation(value = "Get blacklist as Short Accounts based on current token in Headers ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/bl")
    public ResponseEntity<List<AccountShortDto>> getBL(@RequestHeader HttpHeaders headers) {
        return accountFacade.getBlackList(utils.extractToken(headers));
    }
    @ApiOperation(value = "Blacklist accountId provided by current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping("/bl")
    public HttpStatus addBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.addToBlackList(utils.extractToken(headers), accountId);
    }
    @ApiOperation(value = "Remove from blacklist accountId provided by current token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @DeleteMapping("/bl")
    public HttpStatus removeBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.removeFromBlackList(utils.extractToken(headers), accountId);
    }
}
