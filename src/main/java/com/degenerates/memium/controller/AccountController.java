package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AccountFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    AccountFacade accountFacade;

    //                          AVATAR
    @GetMapping("/avatar")
    public ResponseEntity<Image> getAvatar(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getAvatar(token);
    }

    @PostMapping("/avatar")
    public ResponseEntity<Image> setAvatar(@RequestHeader HttpHeaders headers, @RequestBody ImageDto imageDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.setAvatar(token, imageDto);
    }

    @DeleteMapping("/avatar")
    public HttpStatus deleteCurrentAvatar(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.deleteCurrentAvatar(token);
    }





    //                          ACCOUNT
    @GetMapping
    public ResponseEntity<AccountDto> getAccount(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getAccount(token);
    }





    //                          EMAIL, DETAILS
    @GetMapping("/details")
    public ResponseEntity<AccountDetailsDto> getDetails(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getAccountDetails(token);
    }

    @PutMapping("/details")
    public ResponseEntity<AccountDetailsDto> updateDetails(@RequestHeader HttpHeaders headers, @RequestBody AccountDetailsDto accountDetailsDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.updateAccountDetails(token, accountDetailsDto);
    }

    @PutMapping("/email")
    public ResponseEntity<AccountDto> updateAccount(@RequestHeader HttpHeaders headers, UpdatePasswordEmailDto updatePasswordEmailDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.updateAccount(token, updatePasswordEmailDto);
    }




    //                          SUBSCRIBE
    @GetMapping("/sub")
    public ResponseEntity<List<AccountShortDto>> getSub(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getSubscriptions(token);
    }

    @PostMapping("/sub")
    public HttpStatus subscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.subscribe(token, accountId);
    }

    @DeleteMapping("/sub")
    public HttpStatus unSubscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.unsubscribe(token, accountId);
    }




    //                          LIKES
    @GetMapping("/like")
    public ResponseEntity<List<AccountShortDto>> getLikes(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getLikes(token);
    }

    @PostMapping("/like")
    public HttpStatus like(@RequestHeader HttpHeaders headers, UUID articleId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.like(token, articleId);
    }

    @DeleteMapping("/like")
    public HttpStatus unLike(@RequestHeader HttpHeaders headers, UUID articleId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.unlike(token, articleId);
    }






    //                          BLACKLIST
    @GetMapping("/bl")
    public ResponseEntity<List<AccountShortDto>> getBL(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getBlackList(token);
    }

    @PostMapping("/bl")
    public HttpStatus addBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.addToBlackList(token, accountId);
    }

    @DeleteMapping("/bl")
    public HttpStatus removeBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.removeFromBlackList(token, accountId);
    }
}
