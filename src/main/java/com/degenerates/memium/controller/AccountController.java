package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AccountFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    AccountFacade accountFacade;

    @Autowired
    Utils utils;

    //                          AVATAR
    @GetMapping("/avatar")
    public ResponseEntity<Image> getAvatar(@RequestHeader HttpHeaders headers) {
        return accountFacade.getAvatar(utils.extractToken(headers));
    }

    @PostMapping("/avatar")
    public ResponseEntity<Image> setAvatar(@RequestHeader HttpHeaders headers, @RequestParam("image") MultipartFile image) {
        return accountFacade.setAvatar(utils.extractToken(headers), image);
    }

    @DeleteMapping("/avatar")
    public HttpStatus deleteCurrentAvatar(@RequestHeader HttpHeaders headers) {
        return accountFacade.deleteCurrentAvatar(utils.extractToken(headers));
    }





    //                          ACCOUNT
    @GetMapping
    public ResponseEntity<AccountDto> getAccount(@RequestHeader HttpHeaders headers) {
        return accountFacade.getAccount(utils.extractToken(headers));
    }





    //                          EMAIL, DETAILS
    @GetMapping("/details")
    public ResponseEntity<AccountDetailsDto> getDetails(@RequestHeader HttpHeaders headers) {
        return accountFacade.getAccountDetails(utils.extractToken(headers));
    }

    @PutMapping("/details")
    public ResponseEntity<AccountDetailsDto> updateDetails(@RequestHeader HttpHeaders headers, @RequestBody AccountDetailsDto accountDetailsDto) {
        return accountFacade.updateAccountDetails(utils.extractToken(headers), accountDetailsDto);
    }

    @PutMapping("/email")
    public ResponseEntity<AccountDto> updateAccount(@RequestHeader HttpHeaders headers, UpdatePasswordEmailDto updatePasswordEmailDto) {
        return accountFacade.updateAccount(utils.extractToken(headers), updatePasswordEmailDto);
    }




    //                          SUBSCRIBE
    @GetMapping("/sub")
    public ResponseEntity<List<AccountShortDto>> getSub(@RequestHeader HttpHeaders headers) {
        return accountFacade.getSubscriptions(utils.extractToken(headers));
    }

    @PostMapping("/sub")
    public HttpStatus subscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.subscribe(utils.extractToken(headers), accountId);
    }

    @DeleteMapping("/sub")
    public HttpStatus unSubscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.unsubscribe(utils.extractToken(headers), accountId);
    }




    //                          LIKES
    @GetMapping("/like")
    public ResponseEntity<List<ArticleShortDto>> getLikes(@RequestHeader HttpHeaders headers) {
        return accountFacade.getLikes(utils.extractToken(headers));
    }

    @PostMapping("/like")
    public HttpStatus like(@RequestHeader HttpHeaders headers, UUID articleId) {
        return accountFacade.like(utils.extractToken(headers), articleId);
    }

    @DeleteMapping("/like")
    public HttpStatus unLike(@RequestHeader HttpHeaders headers, UUID articleId) {
        return accountFacade.unlike(utils.extractToken(headers), articleId);
    }






    //                          BLACKLIST
    @GetMapping("/bl")
    public ResponseEntity<List<AccountShortDto>> getBL(@RequestHeader HttpHeaders headers) {
        return accountFacade.getBlackList(utils.extractToken(headers));
    }

    @PostMapping("/bl")
    public HttpStatus addBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.addToBlackList(utils.extractToken(headers), accountId);
    }

    @DeleteMapping("/bl")
    public HttpStatus removeBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        return accountFacade.removeFromBlackList(utils.extractToken(headers), accountId);
    }
}
