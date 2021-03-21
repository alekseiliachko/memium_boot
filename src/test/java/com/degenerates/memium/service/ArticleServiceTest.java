package com.degenerates.memium.service;

import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    void checkIfExists() {
        UUID id = UUID.randomUUID();

        when(articleRepository.existsById(id)).thenReturn(true);

        assertTrue(articleService.checkIfExists(id));
    }

    @Test
    public void save() {
        assertDoesNotThrow(() -> {
            articleService.save(new Article());
        });
    }

    @Test
    public void deleteById() {
        assertDoesNotThrow(() -> {
            articleService.deleteById(UUID.randomUUID());
        });
    }
}