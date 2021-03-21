package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.repository.AccountImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountImageServiceTest {

    @Mock
    AccountImageRepository repository;

    @InjectMocks
    AccountImageService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNotFound() {
        when(repository.findByAccountId(any(UUID.class))).thenReturn(Optional.empty());

        assertNotNull(service.getByAccountIdSAFE(UUID.randomUUID()));
    }

    @Test
    public void testFound() {

        AccountImage accountImage = new AccountImage();

        when(repository.findByAccountId(any(UUID.class))).thenReturn(Optional.of(accountImage));

        assertEquals(accountImage, service.getByAccountId(UUID.randomUUID()));
    }

    @Test
    public void save() {

        AccountImage accountImage = new AccountImage();

        when(repository.save(accountImage)).thenReturn(accountImage);

        AccountImage saved = service.save(accountImage);

        assertEquals(accountImage, saved);
    }

    @Test
    public void getByAccountIdSAFE() {

        UUID accountId = UUID.randomUUID();

        AccountImage accountImage = new AccountImage();
        accountImage.setAccountId(accountId);

        when(repository.findByAccountId(accountId)).thenReturn(Optional.of(accountImage));

        AccountImage found = service.getByAccountIdSAFE(accountId);

        assertNotNull(found);
        assertEquals(accountImage, found);
    }

    @Test
    public void deleteByAccountId() {

        UUID accountId = UUID.randomUUID();

        assertDoesNotThrow(() -> service.deleteByAccountId(accountId));
    }

    @Test
    public void checkIfAlreadySet() {

        UUID accountId = UUID.randomUUID();

        when(repository.existsByAccountId(accountId)).thenReturn(true);

        Boolean exists = service.checkIfAlreadySet(accountId);

        assertTrue(exists);
    }
}