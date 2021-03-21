package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.repository.AccountRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNotFound() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.getById(UUID.randomUUID());
        });
    }

    @Test
    public void testFound() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(new Account()));

        assertDoesNotThrow(() -> {
            service.getById(UUID.randomUUID());
        });
    }

    @Test
    public void getByUsername() {

        String uesrname = "asdads";

        when(repository.findByUsername(uesrname)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.getByUsername(uesrname);
        });
    }

    @Test
    public void getByUsernameR() {

        String uesrname = "asdads";

        Account account = new Account();

        when(repository.findByUsernameRegex(uesrname)).thenReturn(Lists.list(account));

        assertEquals(Lists.list(account), service.getByUsernameR(uesrname));
    }

    @Test
    public void delete() {
        assertDoesNotThrow(() -> {
            service.deleteById(UUID.randomUUID());
        });
    }
}