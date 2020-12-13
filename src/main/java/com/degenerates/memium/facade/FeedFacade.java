package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.model.dto.QueryDto;
import com.degenerates.memium.service.*;
import com.degenerates.memium.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FeedFacade {

    @Autowired
    AccountShortService accountShortService;

    @Autowired
    ArticleService articleService;

    @Autowired
    SubService subService;

    @Autowired
    Utils utils;

    public ResponseEntity<QueryDto> getFeedForUserToken(String token) {
        QueryDto queryDto = new QueryDto();

        Account account = utils.validateTokenAndGetOwner(token);
        List<UUID> accountIds = subService.getAccountSubbedBy(account.getAccountId());
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(accountIds);

        log.info("Found " + accountShortDtoList.size() + " subbed accounts for account with Id: " + account.getAccountId());

        List<ArticleShortDto> articleShortDtoList =
                articleService.getLatestByAuthorIds(accountIds)
                .stream().map(Article::toArticleShortDto)
                .collect(Collectors.toList());

        log.info("Found " + articleShortDtoList.size() + " feed-articles for account with Id: " + account.getAccountId());

        queryDto.setArticles(articleShortDtoList);
        queryDto.setAccounts(accountShortDtoList);

        return ResponseEntity.ok(queryDto);
    }
}
