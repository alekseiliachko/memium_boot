package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AccountFacade;
import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.ImageDto;
import com.degenerates.memium.model.dto.UpdatePasswordEmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAvatar(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getAvatar(token);
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> setAvatar(@RequestHeader HttpHeaders headers, @RequestBody ImageDto imageDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.setAvatar(token, imageDto);
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteCurrentAvatar(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.deleteCurrentAvatar(token);
    }





    //                          ACCOUNT
    @GetMapping
    public ResponseEntity<?> getAccount(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getAccount(token);
    }





    //                          EMAIL, DETAILS
    @GetMapping("/details")
    public ResponseEntity<?> getDetails(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getAccountDetails(token);
    }

    @PutMapping("/details")
    public ResponseEntity<?> updateDetails(@RequestHeader HttpHeaders headers, @RequestBody AccountDetailsDto accountDetailsDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.updateAccountDetails(token, accountDetailsDto);
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateAccount(@RequestHeader HttpHeaders headers, UpdatePasswordEmailDto updatePasswordEmailDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.updateAccount(token, updatePasswordEmailDto);
    }




    //                          SUBSCRIBE
    @GetMapping("/sub")
    public ResponseEntity<?> getSub(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getSubscriptions(token);
    }

    @PostMapping("/sub")
    public ResponseEntity<?> subscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.subscribe(token, accountId);
    }

    @DeleteMapping("/sub")
    public ResponseEntity<?> unSubscribe(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.unsubscribe(token, accountId);
    }




    //                          LIKES
    @GetMapping("/like")
    public ResponseEntity<?> getLikes(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getLikes(token);
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestHeader HttpHeaders headers, UUID articleId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.like(token, articleId);
    }

    @DeleteMapping("/like")
    public ResponseEntity<?> unLike(@RequestHeader HttpHeaders headers, UUID articleId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.unlike(token, articleId);
    }






    //                          BLACKLIST
    @GetMapping("/bl")
    public ResponseEntity<?> getBL(@RequestHeader HttpHeaders headers) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.getBlackList(token);
    }

    @PostMapping("/bl")
    public ResponseEntity<?> addBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.addToBlackList(token, accountId);
    }

    @DeleteMapping("/bl")
    public ResponseEntity<?> removeBL(@RequestHeader HttpHeaders headers, UUID accountId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return accountFacade.removeFromBlackList(token, accountId);
    }
}
