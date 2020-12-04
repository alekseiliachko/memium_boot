package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.model.relations.BlackList;
import com.degenerates.memium.model.relations.LikeList;
import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.*;
import com.degenerates.memium.util.Validators;
import com.sun.deploy.xml.BadTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    Validators validators;

    @Autowired
    ImageService imageService;

    private List<AccountShortDto> getAccountShortDtoFromList(List<UUID> list) {
        List<Account> accountList = accountService.getByIds(list);
        List<AccountDetails> accountDetailsList = accountDetailsService.getByAccountIds(list);
        List<AccountShortDto> accountShortDtoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AccountShortDto accountShortDto = new AccountShortDto();
            accountShortDto.setBio(accountDetailsList.get(i).getBio());
            accountShortDto.setName(accountDetailsList.get(i).getName());
            accountShortDto.setUsername(accountList.get(i).getUsername());
            accountShortDto.setImageData(imageService.getByAccountId(list.get(i)).getImage());
            accountShortDtoList.add(accountShortDto);
        }
        return accountShortDtoList;
    }

    public ResponseEntity<Image> getAvatar(String token) {
        Account account = validators.validateTokenAndGetOwner(token);
        Image image = imageService.getByAccountId(account.getAccountId());
        return ResponseEntity.ok(image);
    }

    public ResponseEntity<Image> setAvatar(String token, ImageDto imageDto) {

        Account account = validators.validateTokenAndGetOwner(token);
        Image image = imageService.getByAccountId(account.getAccountId());
        return ResponseEntity.ok(image);
    }

    public HttpStatus deleteCurrentAvatar(String token) {
        Account account = validators.validateTokenAndGetOwner(token);

        return HttpStatus.OK;
    }



    public ResponseEntity<AccountDto> getAccount(String token) {
        return ResponseEntity.ok(validators.validateTokenAndGetOwner(token).toAccountDto());
    }

    public ResponseEntity<AccountDto> getAccountById(UUID accountId) {
        return ResponseEntity.ok(accountService.getById(accountId).toAccountDto());
    }

    public ResponseEntity<AccountDto> updateAccount(String token, UpdatePasswordEmailDto updatePasswordEmailDto) {

        Account account = validators.validateTokenAndGetOwner(token);

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

        Account account = validators.validateTokenAndGetOwner(token);

        return ResponseEntity.ok(accountDetailsService.getByAccountId(account.getAccountId()).toAccountDetailsDto());
    }

    public ResponseEntity<AccountDetailsDto> updateAccountDetails(String token, AccountDetailsDto accountDetailsDto) {

        Account account = validators.validateTokenAndGetOwner(token);

        accountDetailsService.deleteById(account.getAccountId());
        return ResponseEntity.ok(accountDetailsService.save(accountDetailsDto.toAccountDetails()).toAccountDetailsDto());
    }



    public ResponseEntity<List<AccountShortDto>> getSubscriptions(String token) {

        Account account = validators.validateTokenAndGetOwner(token);
        List<AccountShortDto> accountShortDtoList = getAccountShortDtoFromList(subService.getAccountData(
                account.getAccountId()).stream().map(SubList::getSubId).collect(Collectors.toList()));

        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus subscribe(String token, UUID accountId) {

        Account account = validators.validateTokenAndGetOwner(token);

        subService.byAccountSubToAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }

    public HttpStatus unsubscribe(String token, UUID accountId) {

        Account account = validators.validateTokenAndGetOwner(token);

        subService.byAccountUnsubAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }



    public ResponseEntity<List<AccountShortDto>> getLikes(String token) {

        Account account = validators.validateTokenAndGetOwner(token);
        List<AccountShortDto> accountShortDtoList =  getAccountShortDtoFromList(likeService.getAccountData(
                account.getAccountId()).stream().map(LikeList::getArticleId).collect(Collectors.toList()));

        return ResponseEntity.ok(accountShortDtoList);
    }

    public HttpStatus like(String token, UUID articleId) {

        Account account = validators.validateTokenAndGetOwner(token);

        likeService.byAccountLikePost(account.getAccountId(), articleId);

        return HttpStatus.OK;
    }

    public HttpStatus unlike(String token, UUID articleId) {

        Account account = validators.validateTokenAndGetOwner(token);

        likeService.byAccountUnlikeAccount(account.getAccountId(), articleId);

        return HttpStatus.OK;
    }




    public ResponseEntity<List<AccountShortDto>> getBlackList(String token) {

        Account account = validators.validateTokenAndGetOwner(token);
        List<AccountShortDto> list = getAccountShortDtoFromList(blackListService.getAccountData(
                account.getAccountId()).stream().map(BlackList::getBlockedId).collect(Collectors.toList()));

        return ResponseEntity.ok(list);
    }

    public HttpStatus addToBlackList(String token, UUID accountId) {

        Account account = validators.validateTokenAndGetOwner(token);

        blackListService.byAccountBlockAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }

    public HttpStatus removeFromBlackList(String token, UUID accountId) {

        Account account = validators.validateTokenAndGetOwner(token);

        blackListService.byAccountUnblockAccount(account.getAccountId(), accountId);

        return HttpStatus.OK;
    }
}
