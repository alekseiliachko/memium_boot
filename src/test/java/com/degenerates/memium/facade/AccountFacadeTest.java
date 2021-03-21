package com.degenerates.memium.facade;

import com.degenerates.memium.exceptions.CorruptedFileException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.model.enums.Category;
import com.degenerates.memium.service.*;
import com.degenerates.memium.util.Utils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountFacadeTest {

    @Mock
    private AccountService accountService;

    @Mock
    private ArticleService articleService;

    @Mock
    private AccountDetailsService accountDetailsService;

    @Mock
    private BlackListService blackListService;

    @Mock
    private SubService subService;

    @Mock
    private LikeService likeService;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private Utils utils;

    @Mock
    private AccountImageService accountImageService;

    @Mock
    private AccountShortService accountShortService;

    @InjectMocks
    private AccountFacade accountFacade;

    private byte[] getAvatar() {
        return new byte[100];
    }

    private byte[] setAvatar() {
        return new byte[100];
    }

    private static final UUID accountID_ = UUID.randomUUID();

    Account getAccount_() {
        Account account = new Account();
        account.setAccountId(accountID_);
        account.setCreated(new Date());
        account.setEmail("email");
        account.setUsername("username");
        account.setPassword("asdasdsd");

        return account;
    }

    AccountDetails getAccountDetails_() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountId(accountID_);
        accountDetails.setGender("gender");
        return accountDetails;
    }

    Article getArticle_() {
        Article article = new Article();
        article.setTitle("title");
        article.setDescription("asdsad");
        article.setData("something");
        article.setCategory(Category.Anime);
        article.setArticleId(UUID.randomUUID());
        article.setAuthorId(accountID_);
        article.setDate(new Date());

        return article;
    }

    private static final String TOKEN = "ailaiwhf983rhj4brnlkdspoi09132iueeldndsakjfdyuv2983e023jeo2hflkajgfft8238e023j";

    @Test
    void deleteCurrentAvatar() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> accountFacade.deleteCurrentAvatar(TOKEN));
    }

    @Test
    void getAccount() {

        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertEquals(account.convertToAccountDto(), accountFacade.getAccount(TOKEN).getBody());
    }

    @Test
    void getAccountById() {
        Account account = getAccount_();
        UUID accountId = account.getAccountId();

        account.setAccountId(accountId);

        when(accountService.getById(accountId)).thenReturn(account);

        assertEquals(account.getAccountId(), accountFacade.getAccountById(accountId).getBody().getId());
    }

    @Test
    void updateAccount() {
        UpdatePasswordEmailDto updatePasswordEmailDto = new UpdatePasswordEmailDto();
        updatePasswordEmailDto.setEmail("asdasd");
        updatePasswordEmailDto.setPassword("asdsad");

        Account account = getAccount_();

        when(encoder.encode(any())).thenReturn("asdasdsad");
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);
        when(accountService.save(account)).thenReturn(account);

        assertEquals(updatePasswordEmailDto.getEmail(), accountFacade.updateAccount(TOKEN, updatePasswordEmailDto).getBody().getEmail());
    }

    @Test
    void getAccountDetailsById() {

        AccountDetails accountDetails = getAccountDetails_();

        when(accountDetailsService.getByAccountId(accountDetails.getAccountId())).thenReturn(accountDetails);

        assertEquals(accountDetails.toAccountDetailsDto(), accountFacade.getAccountDetailsById(accountDetails.getAccountId()).getBody());
    }

    @Test
    void getAccountDetails() {

        Account account = getAccount_();
        AccountDetails accountDetails = getAccountDetails_();

        account.setAccountId(UUID.randomUUID());
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);
        when(accountDetailsService.getByAccountId(account.getAccountId())).thenReturn(accountDetails);

        assertEquals(accountDetails.toAccountDetailsDto(), accountFacade.getAccountDetails(TOKEN).getBody());
    }

    @Test
    void updateAccountDetails() {

        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);
        AccountDetails accountDetails = getAccountDetails_();

         when(accountDetailsService.save(accountDetails)).thenReturn(accountDetails);

        assertEquals(accountDetails.toAccountDetailsDto(), accountFacade.updateAccountDetails(TOKEN, accountDetails.toAccountDetailsDto()).getBody());
    }


    @Test
    void getSubscriptions() {

        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        UUID uuid = UUID.randomUUID();

        when(subService.getAccountSubbedBy(account.getAccountId())).thenReturn(Lists.list(uuid));
        when(accountShortService.getAccountsByIds(Lists.list(uuid))).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), accountFacade.getSubscriptions(TOKEN).getBody());
    }

    @Test
    void subscribe() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> {
            accountFacade.subscribe(TOKEN, UUID.randomUUID());
        });
    }

    @Test
    void unsubscribe() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> {
            accountFacade.unsubscribe(TOKEN, UUID.randomUUID());
        });
    }


    @Test
    void getLikes() {

        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        Article article = getArticle_();

        List<UUID> ids = Lists.list(UUID.randomUUID());

        when(likeService.getAccountData(account.getAccountId())).thenReturn(ids);
        when(articleService.getByArticleIds(ids)).thenReturn(Lists.list(article));

        assertEquals(Lists.list(article.toArticleShortDto()), accountFacade.getLikes(TOKEN).getBody());
    }

    @Test
    void like() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> {
            accountFacade.like(TOKEN, UUID.randomUUID());
        });
    }

    @Test
    void unlike() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> {
            accountFacade.like(TOKEN, UUID.randomUUID());
        });
    }


    @Test
    void getBlackList() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);



        List<AccountShortDto> accountShortDtoList = Lists.list(new AccountShortDto());
        when(accountShortService.getAccountsByIds(
                blackListService.getAccountData(account.getAccountId()))).thenReturn((Lists.list(new AccountShortDto())));

        assertEquals(Lists.list(new AccountShortDto()), accountFacade.getBlackList(TOKEN).getBody());
    }

    @Test
    void addToBlackList() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> {
            accountFacade.addToBlackList(TOKEN, UUID.randomUUID());
        });
    }

    @Test
    void removeFromBlackList() {
        Account account = getAccount_();
        when(utils.validateTokenAndGetOwner(TOKEN)).thenReturn(account);

        assertDoesNotThrow(() -> {
            accountFacade.removeFromBlackList(TOKEN, UUID.randomUUID());
        });
    }
}
