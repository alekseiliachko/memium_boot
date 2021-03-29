package com.degenerates.memium.service;

import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.repository.SubListRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubServiceTest {

    @Mock
    SubListRepository subListRepository;

    @InjectMocks
    SubService subService;

    private static final UUID accountId = UUID.randomUUID();

    private static final List<UUID> subToId = subToId();

    private static final List<SubList> DATA = getData();

    private static List<SubList> getData() {
        return Lists.list(
                new SubList(UUID.randomUUID(),accountId, subToId.get(0)),
                new SubList(UUID.randomUUID(),accountId, subToId.get(1)),
                new SubList(UUID.randomUUID(),accountId, subToId.get(2)),
                new SubList(UUID.randomUUID(),accountId, subToId.get(3))
        );
    }

    private static List<UUID> subToId() {
        return Lists.list(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    @Test
    void getDataTest() {
        when(subListRepository.findByAccountId(accountId)).thenReturn(DATA);

        List<UUID> found = subService.getAccountSubbedBy(accountId);

        assertEquals(subToId, found);
    }

    @Test
    void sub() {
        assertDoesNotThrow(() -> {
            subService.byAccountSubToAccount(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void unsub() {
        assertDoesNotThrow(() -> {
            subService.byAccountUnsubAccount(UUID.randomUUID(), UUID.randomUUID());
        });
    }
}
