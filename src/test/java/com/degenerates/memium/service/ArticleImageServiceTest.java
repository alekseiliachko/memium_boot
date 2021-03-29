package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.OptionalEntityNotFoundException;
import com.degenerates.memium.model.dao.ArticleImage;
import com.degenerates.memium.repository.ArticleImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleImageServiceTest {

    @Mock
    ArticleImageRepository articleImageRepository;

    @InjectMocks
    ArticleImageService articleImageService;

    private static final ArticleImage articleImage = new ArticleImage(UUID.randomUUID(), new byte[12]);

    @Test
    void save() {
        assertDoesNotThrow(() -> articleImageService.save(articleImage));
    }

    @Test
    void getByArticleId() {
        when(articleImageRepository.findByArticleId(articleImage.getArticleId())).thenReturn(java.util.Optional.of(articleImage));

        assertDoesNotThrow(() ->
                articleImageService.getByArticleId(articleImage.getArticleId()));
    }

    @Test
    void checkIfAlreadySet() {

        when(articleImageRepository.existsByArticleId(articleImage.getArticleId())).thenReturn(true);

        assertTrue(articleImageService.checkIfAlreadySet(articleImage.getArticleId()));
    }

    @Test
    void deleteByArticleId() {
        assertDoesNotThrow(() -> articleImageService.deleteByArticleId(UUID.randomUUID()));
    }
}
