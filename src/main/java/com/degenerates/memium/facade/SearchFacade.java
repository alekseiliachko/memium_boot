package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.model.dto.QueryDto;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.AccountShortService;
import com.degenerates.memium.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SearchFacade {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountShortService accountShortService;

    @Autowired
    ArticleService articleService;

    public ResponseEntity<QueryDto> findByCategory(String string) {
        QueryDto queryDto = new QueryDto();

        List<ArticleShortDto> articleShortDtoListCategory = articleService.getByCategoryR(string)
                .stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());

        log.info("Found " + articleShortDtoListCategory.size() + " articles for category-search: " + string);

        queryDto.setArticles(articleShortDtoListCategory);

        return ResponseEntity.ok(queryDto);
    }

    public ResponseEntity<QueryDto> findByArticle(String string) {
        QueryDto queryDto = new QueryDto();

        List<ArticleShortDto> articleShortDtoListCategory = articleService.getByTitleR(string)
                .stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());

        log.info("Found " + articleShortDtoListCategory.size() + " articles for article-search: " + string);

        queryDto.setArticles(articleShortDtoListCategory);

        return ResponseEntity.ok(queryDto);
    }

    public ResponseEntity<QueryDto> findByAccount(String string) {
        QueryDto queryDto = new QueryDto();

        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(
                accountService.getByUsernameR(string)
                        .stream().map(Account::getAccountId)
                        .collect(Collectors.toList()));

        log.info("Found " + accountShortDtoList.size() + " accounts for account-search: " + string);


        queryDto.setAccounts(accountShortDtoList);

        return ResponseEntity.ok(queryDto);
    }

    public ResponseEntity<QueryDto> findByString(String string) {
        QueryDto queryDto = new QueryDto();
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(
                accountService.getByUsernameR(string)
                        .stream().map(Account::getAccountId)
                        .collect(Collectors.toList()));

        log.info("Found " + accountShortDtoList.size() + " accounts for all-search: " + string);


        List<ArticleShortDto> articleShortDtoList = articleService.getByTitleR(string)
                .stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());

        log.info("Found " + articleShortDtoList.size() + " articles by title for all-search: " + string);


        List<ArticleShortDto> articleShortDtoListCategory = articleService.getByCategoryR(string)
                .stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());

        log.info("Found " + articleShortDtoListCategory.size() + " articles by category for all-search: " + string);

        articleShortDtoList.addAll(articleShortDtoListCategory);

        queryDto.setAccounts(accountShortDtoList);
        queryDto.setArticles(articleShortDtoList);

        return ResponseEntity.ok(queryDto);
    }

}
