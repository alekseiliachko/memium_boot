package com.degenerates.memium.facade;

import com.degenerates.memium.service.LikeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeFacadeTest {

    @Mock
    LikeService likeService;

    @InjectMocks
    LikeFacade likeFacade;

    private static final UUID articleId = UUID.randomUUID();

    @Test
    void getLikeAmountForArticle() {

        when(likeService.getLikeCountForArticle(articleId)).thenReturn(3L);
        assertEquals(3L, likeFacade.getLikeAmountForArticle(articleId).getBody());
    }
}
