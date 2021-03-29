package com.degenerates.memium.service;

import com.degenerates.memium.model.relations.BlackList;
import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.repository.ArticleRepository;
import com.degenerates.memium.repository.BlackListRepository;
import com.degenerates.memium.repository.LikeListRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlackListServiceTest {

    @Mock
    BlackListRepository blackListRepository;

    @Mock
    LikeListRepository likeListRepository;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    BlackListService blackListService;

    private static final UUID accountId = UUID.randomUUID();

    private static final List<UUID> blockedList = blocked();

    private static final List<BlackList> DATA = getData();

    private static List<BlackList> getData() {
        return Lists.list(
                new BlackList(UUID.randomUUID(),accountId, blockedList.get(0)),
                new BlackList(UUID.randomUUID(),accountId, blockedList.get(1)),
                new BlackList(UUID.randomUUID(),accountId, blockedList.get(2)),
                new BlackList(UUID.randomUUID(),accountId, blockedList.get(3))
        );
    }

    private static List<UUID> blocked() {
        return Lists.list(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    @Test
    void getDataTest() {
        when(blackListRepository.findByAccountId(accountId)).thenReturn(DATA);

        List<UUID> found = blackListService.getAccountData(accountId);

        assertEquals(blockedList, found);
    }

    @Test
    void sub() {

        UUID blockedId = UUID.randomUUID();
        when(blackListRepository.existsByAccountIdAndBlockedId(accountId, blockedId)).thenReturn(true);
        when(articleRepository.findByAuthorId(blockedId)).thenReturn(new ArrayList<>());
        assertDoesNotThrow(() -> {
            blackListService.byAccountBlockAccount(accountId, blockedId);
        });
    }

    @Test
    void unsub() {
        assertDoesNotThrow(() -> {
            blackListService.byAccountUnblockAccount(UUID.randomUUID(), UUID.randomUUID());
        });
    }
}
