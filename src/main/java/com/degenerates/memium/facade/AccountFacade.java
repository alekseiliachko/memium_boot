package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.CorruptedFileException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.model.relations.BlackList;
import com.degenerates.memium.model.relations.LikeList;
import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.*;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountFacade {

    @Autowired
    AccountService accountService;

    @Autowired
    ArticleService articleService;

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

    @Autowired
    Utils utils;

    @Autowired
    ImageService imageService;

    @Autowired
    AccountShortService accountShortService;

    public ResponseEntity<Image> getAvatar(String token) {
        Account account = utils.validateTokenAndGetOwner(token);
        Image image = imageService.getByAccountId(account.getAccountId());
        return ResponseEntity.ok(image);
    }

    public ResponseEntity<Image> getAvatar(UUID id) {
        Image image = imageService.getByAccountId(id);
        return ResponseEntity.ok(image);
    }

    public ResponseEntity<Image> setAvatar(String token, MultipartFile imageFile) {

        Account account = utils.validateTokenAndGetOwner(token);
        Image image = new Image();
        image.setAccountId(account.getAccountId());
        try {
            image.setImage(imageFile.getBytes());
        } catch (IOException e) {
            throw new CorruptedFileException();
        }
        image = imageService.save(image);
        return ResponseEntity.ok(image);
    }

    public HttpStatus deleteCurrentAvatar(String token) {
        Account account = utils.validateTokenAndGetOwner(token);

        return HttpStatus.OK;
    }



    public ResponseEntity<AccountDto> getAccount(String token) {
        return ResponseEntity.ok(utils.validateTokenAndGetOwner(token).toAccountDto());
    }

    public ResponseEntity<AccountDto> getAccountById(UUID accountId) {
        return ResponseEntity.ok(accountService.getById(accountId).toAccountDto());
    }

    public ResponseEntity<AccountDto> updateAccount(String token, UpdatePasswordEmailDto updatePasswordEmailDto) {

        Account account = utils.validateTokenAndGetOwner(token);

        if (updatePasswordEmailDto.getEmail() != null)
            account.setEmail(updatePasswordEmailDto.getEmail());
        if (updatePasswordEmailDto.getPassword() != null)
            account.setPassword(encoder.encode(updatePasswordEmailDto.getPassword()));

        return ResponseEntity.ok(accountService.save(account).toAccountDto());
    }

    public ResponseEntity<AccountDetailsDto> getAccountDetailsById(UUID accountId) {
        return ResponseEntity.ok(accountDetailsService.getByAccountId(accountId).toAccountDetailsDto());
    }

    public ResponseEntity<AccountDetailsDto> getAccountDetails(String token) {

        Account account = utils.validateTokenAndGetOwner(token);

        return ResponseEntity.ok(accountDetailsService.getByAccountId(account.getAccountId()).toAccountDetailsDto());
    }

    public ResponseEntity<AccountDetailsDto> updateAccountDetails(String token, AccountDetailsDto accountDetailsDto) {

        Account account = utils.validateTokenAndGetOwner(token);

        accountDetailsService.deleteById(account.getAccountId());
        return ResponseEntity.ok(accountDetailsService.save(accountDetailsDto.toAccountDetails()).toAccountDetailsDto());
    }



    public ResponseEntity<List<AccountShortDto>> getSubscriptions(String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(
                subService.getAccountSubbedBy(account.getAccountId()));

        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus subscribe(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);

        subService.byAccountSubToAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }

    public HttpStatus unsubscribe(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);

        subService.byAccountUnsubAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }



    public ResponseEntity<List<ArticleShortDto>> getLikes(String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        List<ArticleShortDto> accountShortDtoList = articleService.getByAccountId(account.getAccountId()).
                stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus like(String token, UUID articleId) {

        Account account = utils.validateTokenAndGetOwner(token);

        likeService.byAccountLikePost(account.getAccountId(), articleId);

        return HttpStatus.OK;
    }

    public HttpStatus unlike(String token, UUID articleId) {

        Account account = utils.validateTokenAndGetOwner(token);

        likeService.byAccountUnlikeAccount(account.getAccountId(), articleId);

        return HttpStatus.OK;
    }




    public ResponseEntity<List<AccountShortDto>> getBlackList(String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(
                likeService.getAccountData(account.getAccountId()));


        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus addToBlackList(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);

        blackListService.byAccountBlockAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }

    public HttpStatus removeFromBlackList(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);

        blackListService.byAccountUnblockAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }
}
