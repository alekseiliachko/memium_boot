package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.CorruptedFileException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.service.*;
import com.degenerates.memium.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
    PasswordEncoder encoder;

    @Autowired
    Utils utils;

    @Autowired
    AccountImageService accountImageService;

    @Autowired
    AccountShortService accountShortService;

    public byte[] getAvatar(String token) {
        Account account = utils.validateTokenAndGetOwner(token);
        AccountImage accountImage = accountImageService.getByAccountId(account.getAccountId());

        log.info("Found image for account: " + account.getAccountId());

        return accountImage.getImage();
    }

    public byte[] getAvatar(UUID id) {
        AccountImage accountImage = accountImageService.getByAccountId(id);

        log.info("Found image for account: " + id);

        return accountImage.getImage();
    }

    public byte[] setAvatar(String token, MultipartFile imageFile) {

        Account account = utils.validateTokenAndGetOwner(token);
        AccountImage accountImage = new AccountImage();

        accountImage.setAccountId(account.getAccountId());
        try {
            accountImage.setImage(imageFile.getBytes());
        } catch (IOException e) {
            throw new CorruptedFileException();
        }
        if (accountImageService.checkIfAlreadySet(account.getAccountId())) {
            accountImageService.deleteByAccountId(account.getAccountId());
            log.info("Avatar already set, deleted: " + account.getAccountId());
        }
        accountImage = accountImageService.save(accountImage);
        log.info("Set avatar for: " + account.getAccountId());
        return accountImage.getImage();
    }

    public HttpStatus deleteCurrentAvatar(String token) {
        Account account = utils.validateTokenAndGetOwner(token);
        accountImageService.deleteByAccountId(account.getAccountId());
        log.info("Deleted avatar for: " + account.getAccountId());
        return HttpStatus.OK;
    }



    public ResponseEntity<AccountDto> getAccount(String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        log.info("Found account with Id: " + account.getAccountId());

        return ResponseEntity.ok(account.convertToAccountDto());
    }

    public ResponseEntity<AccountDto> getAccountById(UUID accountId) {
        Account account = accountService.getById(accountId);
        log.info("Found account by Id: " + accountId);

        return ResponseEntity.ok(account.convertToAccountDto());
    }

    public ResponseEntity<AccountDto> updateAccount(String token, UpdatePasswordEmailDto updatePasswordEmailDto) {

        Account account = utils.validateTokenAndGetOwner(token);

        if (updatePasswordEmailDto.getEmail() != null)
            account.setEmail(updatePasswordEmailDto.getEmail());
        if (updatePasswordEmailDto.getPassword() != null)
            account.setPassword(encoder.encode(updatePasswordEmailDto.getPassword()));

        account = accountService.save(account);
        log.info("Account updated: " + account.getAccountId());

        return ResponseEntity.ok(account.convertToAccountDto());
    }

    public ResponseEntity<AccountDetailsDto> getAccountDetailsById(UUID accountId) {
        AccountDetails accountDetails = accountDetailsService.getByAccountId(accountId);
        log.info("Found accountDetails by Id: " + accountId);
        return ResponseEntity.ok(accountDetails.toAccountDetailsDto());
    }

    public ResponseEntity<AccountDetailsDto> getAccountDetails(String token) {
        Account account = utils.validateTokenAndGetOwner(token);
        AccountDetails accountDetails = accountDetailsService.getByAccountId(account.getAccountId());
        log.info("Found accountDetails with Id: " + account.getAccountId());
        return ResponseEntity.ok(accountDetails.toAccountDetailsDto());
    }

    public ResponseEntity<AccountDetailsDto> updateAccountDetails(String token, AccountDetailsDto accountDetailsDto) {
        Account account = utils.validateTokenAndGetOwner(token);

        AccountDetails accountDetails = accountDetailsDto.toAccountDetails();
        accountDetails.setAccountId(account.getAccountId());
        accountDetails = accountDetailsService.save(accountDetails);

        log.info("AccountDetails updated with Id: " + account.getAccountId());
        return ResponseEntity.ok(accountDetails.toAccountDetailsDto());
    }



    public ResponseEntity<List<AccountShortDto>> getSubscriptions(String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(
                subService.getAccountSubbedBy(account.getAccountId()));
        log.info("Found " + accountShortDtoList.size() +" subscriptions for account with Id: " + account.getAccountId());
        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus subscribe(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);
        subService.byAccountSubToAccount(account.getAccountId(), accountId);
        log.info("Account " + account.getAccountId() + " is now subbed to " + accountId);

        return HttpStatus.OK;
    }

    public HttpStatus unsubscribe(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);
        subService.byAccountUnsubAccount(account.getAccountId(), accountId);
        log.info("Account " + account.getAccountId() + " is now not subbed to " + accountId);

        return HttpStatus.OK;
    }



    public ResponseEntity<List<ArticleShortDto>> getLikes(String token) {

        Account account = utils.validateTokenAndGetOwner(token);

        List<ArticleShortDto> articleShortDtoList = articleService.getByArticleIds(
                likeService.getAccountData(account.getAccountId()))
                .stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());
        log.info("Found " + articleShortDtoList.size() + " likes for account with Id: " + account.getAccountId());

        return ResponseEntity.ok(articleShortDtoList);
    }

    public HttpStatus like(String token, UUID articleId) {

        Account account = utils.validateTokenAndGetOwner(token);
        likeService.byAccountLikePost(account.getAccountId(), articleId);
        log.info("Account " + account.getAccountId() + " has liked " + articleId);

        return HttpStatus.OK;
    }

    public HttpStatus unlike(String token, UUID articleId) {

        Account account = utils.validateTokenAndGetOwner(token);
        likeService.byAccountUnlikeAccount(account.getAccountId(), articleId);
        log.info("Account " + account.getAccountId() + " has disliked " + articleId);

        return HttpStatus.OK;
    }




    public ResponseEntity<List<AccountShortDto>> getBlackList(String token) {

        Account account = utils.validateTokenAndGetOwner(token);
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(
                blackListService.getAccountData(account.getAccountId()));
        log.info("Found " + accountShortDtoList.size() + " blacklist records for account with Id: " + account.getAccountId());


        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus addToBlackList(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);
        blackListService.byAccountBlockAccount(account.getAccountId(), accountId);
        log.info("Account " + account.getAccountId() + " has added " + accountId + " to their blacklist");

        return HttpStatus.OK;
    }

    public HttpStatus removeFromBlackList(String token, UUID accountId) {

        Account account = utils.validateTokenAndGetOwner(token);
        blackListService.byAccountUnblockAccount(account.getAccountId(), accountId);
        log.info("Account " + account.getAccountId() + " has removed " + accountId + " from their blacklist");

        return HttpStatus.OK;
    }
}
