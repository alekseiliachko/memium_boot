package com.degenerates.memium.service;

import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.relations.BlackList;
import com.degenerates.memium.repository.ArticleRepository;
import com.degenerates.memium.repository.BlackListRepository;
import com.degenerates.memium.repository.LikeListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlackListService {

    @Autowired
    BlackListRepository repository;

    @Autowired
    LikeListRepository likeListRepository;

    @Autowired
    ArticleRepository articleRepository;

    public List<UUID> getAccountData(UUID accountId) {
        return repository.findByAccountId(accountId)
                .stream().map(BlackList::getBlockedId)
                .collect(Collectors.toList());
    };

    public void byAccountBlockAccount(UUID accountId, UUID blockId) {
        BlackList blackList = new BlackList(UUID.randomUUID(), accountId, blockId);
        if (!repository.existsByAccountIdAndBlockedId(accountId, blockId))
            repository.save(blackList);
        List<Article> articles = articleRepository.findByAuthorId(blockId);
        articles.forEach(article -> likeListRepository.deleteByArticleIdAndAccountId(article.getArticleId(),accountId));
    }

    public void byAccountUnblockAccount(UUID accountId, UUID blockedId) {
        BlackList blackList = repository.findByAccountIdAndBlockedId(accountId, blockedId);
        if (blackList != null) repository.delete(blackList);
    }
}