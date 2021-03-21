package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.model.enums.ERole;
import com.degenerates.memium.repository.AccountRepository;
import com.degenerates.memium.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AccountShortServiceTest {

    @Mock
    AccountService accountService;

    @Mock
    AccountDetailsService accountDetailsService;

    @Mock
    AccountImageService accountImageService;

    @InjectMocks
    AccountShortService service;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void testFound() throws IOException {

        Account account = objectMapper.readValue(new File("src/test/resources/Account.json"), Account.class);
        AccountDetails accountDetails = objectMapper.readValue(new File("src/test/resources/AccountDetails.json"), AccountDetails.class);
        AccountImage accountImage = objectMapper.readValue(new File("src/test/resources/AccountImage.json"), AccountImage.class);
        AccountShortDto accountShortDto = objectMapper.readValue(new File("src/test/resources/AccountShortDto.json"), AccountShortDto.class);

        when(accountService.getById(account.getAccountId())).thenReturn(account);
        when(accountDetailsService.getByAccountId(accountDetails.getAccountId())).thenReturn(accountDetails);
        when(accountImageService.getByAccountId(accountImage.getAccountId())).thenReturn(accountImage);

        assertEquals(accountShortDto,
                service.getAccountById(account.getAccountId()));
    }

    @Test
    public void testFoundMultiple() throws IOException {

        Account account = objectMapper.readValue(new File("src/test/resources/Account.json"), Account.class);
        AccountDetails accountDetails = objectMapper.readValue(new File("src/test/resources/AccountDetails.json"), AccountDetails.class);
        AccountImage accountImage = objectMapper.readValue(new File("src/test/resources/AccountImage.json"), AccountImage.class);
        AccountShortDto accountShortDto = objectMapper.readValue(new File("src/test/resources/AccountShortDto.json"), AccountShortDto.class);

        when(accountService.getById(account.getAccountId())).thenReturn(account);
        when(accountDetailsService.getByAccountId(accountDetails.getAccountId())).thenReturn(accountDetails);
        when(accountImageService.getByAccountIdSAFE(accountImage.getAccountId())).thenReturn(accountImage);

        assertEquals(Lists.list(accountShortDto),
                service.getAccountsByIds(Lists.newArrayList(account.getAccountId())));
    }
}