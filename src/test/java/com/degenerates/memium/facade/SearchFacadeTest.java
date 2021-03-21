package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.model.dto.QueryDto;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.AccountShortService;
import com.degenerates.memium.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchFacadeTest {

    @Mock
    AccountService accountService;

    @Mock
    AccountShortService accountShortService;

    @Mock
    ArticleService articleService;

    @InjectMocks
    SearchFacade searchFacade;

    @Test
    void findByCategory() {

        assertThrows(NullPointerException.class, () -> searchFacade.findByCategory("sadsad"));
    }

}
