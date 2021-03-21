package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.repository.AccountDetailsRepository;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AccountDetailsServiceTest {

    @Mock
    AccountDetailsRepository accountDetailsRepository;

    @InjectMocks
    AccountDetailsService accountDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNotFound() {
        when(accountDetailsRepository.findByAccountIdIn(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            accountDetailsService.getByAccountId(UUID.randomUUID());
        });
    }

    @Test
    public void testFound() {
        when(accountDetailsRepository.findByAccountIdIn(any(UUID.class))).thenReturn(Optional.of(new AccountDetails()));

        assertDoesNotThrow(() -> {
            accountDetailsService.getByAccountId(UUID.randomUUID());
        });
    }

    @Test
    public void test2Found() {
        when(accountDetailsRepository
                .findByAccountIdIn(any(List.class)))
                .thenReturn(new ArrayList());

        assertEquals(0, accountDetailsService.getByAccountIds(Lists.emptyList()).size());
    }

    @Test
    public void test2NotFound() {
        assertNull(accountDetailsService.save(new AccountDetails()));
    }

    @Test
    public void save() {
        AccountDetails accountDetails = new AccountDetails();
        when(accountDetailsRepository.save(accountDetails)).thenReturn(accountDetails);

        AccountDetails saved = accountDetailsService.save(accountDetails);

        assertEquals(accountDetails, saved);
    }

    @Test
    public void listFind() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountId(UUID.randomUUID());
        List<AccountDetails> list = Lists.list(accountDetails);

        when(accountDetailsRepository.findByAccountIdIn(Lists.list(accountDetails.getAccountId()))).thenReturn(list);

        List<AccountDetails> found = accountDetailsService.accountDetailsRepository.findByAccountIdIn(Lists.list(accountDetails.getAccountId()));

        assertEquals(list, found);
    }
}