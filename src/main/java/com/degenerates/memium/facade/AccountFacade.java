package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountDto;
import com.degenerates.memium.model.dto.ImageDto;
import com.degenerates.memium.service.AccountDetailsService;
import com.degenerates.memium.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountFacade {

    public ResponseEntity<?> getAvatar(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> setAvatar(String token, ImageDto imageDto) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> deleteCurrentAvatar(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }



    public ResponseEntity<?> getAccount(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> getAccountById(String accountId) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> saveAccount(AccountDto accountDto) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> getAccountDetailsById(String accountId) {

        String str = "";
        return ResponseEntity.ok(str);
    }



    public ResponseEntity<?> getAccountDetails(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> updateAccountDetails(String token, AccountDetailsDto accountDetailsDto) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> updateEmail(String token, String email) {

        String str = "";
        return ResponseEntity.ok(str);
    }




    public ResponseEntity<?> getSubscriptions(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> subscribe(String token, String accountId) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> unsubscribe(String token, String accountId) {

        String str = "";
        return ResponseEntity.ok(str);
    }



    public ResponseEntity<?> getLikes(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> like(String token, String articleId) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> unlike(String token, String articleId) {

        String str = "";
        return ResponseEntity.ok(str);
    }




    public ResponseEntity<?> getBlackList(String token) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> addToBlackList(String token, String accountId) {

        String str = "";
        return ResponseEntity.ok(str);
    }

    public ResponseEntity<?> removeFromBlackList(String token, String accountId) {

        String str = "";
        return ResponseEntity.ok(str);
    }
}
