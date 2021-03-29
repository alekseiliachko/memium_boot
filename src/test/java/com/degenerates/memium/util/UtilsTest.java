package com.degenerates.memium.util;

import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.exceptions.BadTokenException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

public class UtilsTest {

    @Mock
    JwtUtils jwtUtils;

    @Mock
    AccountService accountService;

    @InjectMocks
    Utils utils;

    String token = "invalidToken";

    UUID uuid;
    Account account;


    @BeforeEach
    public void setUp() {

        uuid = UUID.randomUUID();
        account = new Account();
        account.setAccountId(uuid);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInvalidToken() {
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).thenCallRealMethod();

        Assertions.assertThrows(BadTokenException.class, () -> {
            utils.validateTokenAndGetOwner(token);
        });
    }

    @Test
    public void testValidToken() {
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).thenReturn("username");
        Mockito.when(accountService.getByUsername(Mockito.anyString())).thenReturn(account);

        Assertions.assertDoesNotThrow(() -> {
            utils.validateTokenAndGetOwner(token);
        });
    }

    @Test
    public void accountOwnsItem() {

        Assertions.assertDoesNotThrow(() -> {
            utils.accountOwnsItem(account, uuid);
        });
    }

    @Test
    public void accountDoesNotOwnItem() {

        Assertions.assertThrows(AccessMismatchException.class, () -> {
            utils.accountOwnsItem(account, UUID.randomUUID());
        });
    }

    @Test
    public void extractToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4IiwiaWF0IjoxNjA3NDIyMzY5LCJleHAiOjE2MDc1MDg3Njl9.UUTmQDjnr0vuAanYAaYDxoxFwbcC7rJi3OdpIVpptfA");
            Assertions.assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4IiwiaWF0IjoxNjA3NDIyMzY5LCJleHAiOjE2MDc1MDg3Njl9.UUTmQDjnr0vuAanYAaYDxoxFwbcC7rJi3OdpIVpptfA", utils.extractToken(headers));            ;
    }
}
