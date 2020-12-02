package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.model.dto.AccountDetailsDto;
import com.degenerates.memium.model.dto.AccountDto;
import com.degenerates.memium.model.dto.ImageDto;
import com.degenerates.memium.model.dto.UpdatePasswordEmailDto;
import com.degenerates.memium.model.relations.BlackList;
import com.degenerates.memium.model.relations.LikeList;
import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountFacade {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailsService accountDetailsService;

    @Autowired
    BlackListService blackListService;

    @Autowired
    SubService subService;

    @Autowired
    LikeService likeService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

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

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        return ResponseEntity.ok(account.toAccountDto());
    }

    public ResponseEntity<?> getAccountById(UUID accountId) {
        return ResponseEntity.ok(accountService.getById(accountId));
    }

    public ResponseEntity<?> updateAccount(String token, UpdatePasswordEmailDto updatePasswordEmailDto) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        if (updatePasswordEmailDto.getEmail() != null)
            account.setEmail(updatePasswordEmailDto.getEmail());
        if (updatePasswordEmailDto.getPassword() != null)
            account.setPassword(encoder.encode(updatePasswordEmailDto.getPassword()));

        return ResponseEntity.ok(accountService.save(account).toAccountDto());
    }

    public ResponseEntity<?> getAccountDetailsById(UUID accountId) {
        return ResponseEntity.ok(accountDetailsService.getByAccountId(accountId).toAccountDetailsDto());
    }



    public ResponseEntity<?> getAccountDetails(String token) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        return ResponseEntity.ok(accountDetailsService.getByAccountId(account.getAccountId()).toAccountDetailsDto());
    }

    public ResponseEntity<?> updateAccountDetails(String token, AccountDetailsDto accountDetailsDto) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        accountDetailsService.deleteById(account.getAccountId());
        return ResponseEntity.ok(accountDetailsService.save(accountDetailsDto.toAccountDetails()).toAccountDetailsDto());
    }

    public ResponseEntity<?> getSubscriptions(String token) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        return ResponseEntity.ok(subService.getAccountData(
                account.getAccountId()).stream().map(SubList::getSubId).collect(Collectors.toList()));
    }

    public ResponseEntity<?> subscribe(String token, UUID accountId) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }
        subService.byAccountSubToAccount(account.getAccountId(), accountId);

        return ResponseEntity.ok("subbed");
    }

    public ResponseEntity<?> unsubscribe(String token, UUID accountId) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }
        subService.byAccountUnsubAccount(account.getAccountId(), accountId);

        return ResponseEntity.ok("unsubbed");
    }



    public ResponseEntity<?> getLikes(String token) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        return ResponseEntity.ok(likeService.getAccountData(
                account.getAccountId()).stream().map(LikeList::getArticleId).collect(Collectors.toList()));
    }

    public ResponseEntity<?> like(String token, UUID articleId) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        likeService.byAccountLikePost(account.getAccountId(), articleId);

        return ResponseEntity.ok("liked");
    }

    public ResponseEntity<?> unlike(String token, UUID articleId) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        likeService.byAccountUnlikeAccount(account.getAccountId(), articleId);

        return ResponseEntity.ok("unliked");
    }




    public ResponseEntity<?> getBlackList(String token) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        return ResponseEntity.ok(blackListService.getAccountData(
                account.getAccountId()).stream().map(BlackList::getBlockedId).collect(Collectors.toList()));
    }

    public ResponseEntity<?> addToBlackList(String token, UUID accountId) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        blackListService.byAccountBlockAccount(account.getAccountId(), accountId);

        return ResponseEntity.ok("blocked");
    }

    public ResponseEntity<?> removeFromBlackList(String token, UUID accountId) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Account account = accountService.getByUsername(username);

        if (account == null) {
            return ResponseEntity.badRequest().body("bad token");
        }

        blackListService.byAccountUnblockAccount(account.getAccountId(), accountId);

        return ResponseEntity.ok("unblocked");
    }
}
