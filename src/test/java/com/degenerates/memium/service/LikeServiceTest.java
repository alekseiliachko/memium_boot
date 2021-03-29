package com.degenerates.memium.service;

import com.degenerates.memium.model.relations.LikeList;
import com.degenerates.memium.repository.LikeListRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    LikeListRepository likeListRepository;

    @InjectMocks
    LikeService likeService;

    private static final UUID accountId = UUID.randomUUID();

    private static final List<UUID> articleIds = articleList();

    private static final List<LikeList> DATA = getData();

    private static List<LikeList> getData() {
        return Lists.list(
                new LikeList(UUID.randomUUID(),accountId, articleIds.get(0)),
                new LikeList(UUID.randomUUID(),accountId, articleIds.get(1)),
                new LikeList(UUID.randomUUID(),accountId, articleIds.get(2)),
                new LikeList(UUID.randomUUID(),accountId, articleIds.get(3))
        );
    }

    private static List<UUID> articleList() {
        return Lists.list(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    @Test
    void getDataTest() {
        when(likeListRepository.findByAccountId(accountId)).thenReturn(DATA);

        List<UUID> found = likeService.getAccountData(accountId);

        assertEquals(articleIds, found);
    }

    @Test
    void count() {
        when(likeListRepository.countByArticleId(articleIds.get(0))).thenReturn(5L);

        Long count = likeService.getLikeCountForArticle(articleIds.get(0));

        assertEquals(5L, count);
    }

    @Test
    void check() {
        when(likeListRepository.existsByAccountIdAndArticleId(any(UUID.class), any(UUID.class))).thenReturn(true);

        assertTrue(likeService.checkIfAccountLikedArticle(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void like() {
        assertDoesNotThrow(() -> {
            likeService.byAccountUnlikeAccount(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void unlike() {
        assertDoesNotThrow(() -> {
            likeService.byAccountLikePost(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void unlikeAll() {
        assertDoesNotThrow(() -> {
            likeService.unlikeAllByAticleId(UUID.randomUUID());
        });
    }
}
