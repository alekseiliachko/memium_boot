package com.degenerates.memium.service;

import com.degenerates.memium.model.relations.LikeList;
import com.degenerates.memium.repository.LikeListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    LikeListRepository repository;

    public List<LikeList> getAccountData(UUID accountId) {
        return repository.findByAccountId(accountId);
    };

    public List<LikeList> getLikesForArticle(UUID articleId) {
        return repository.findByArticleId(articleId);
    };

    public void byAccountLikePost(UUID accountId, UUID likeId) {
        LikeList likeList = new LikeList(accountId, likeId);
        if (!repository.existsByAccountIdAndArticleId(accountId, likeId))
            repository.save(likeList);
    }

    public void byAccountUnlikeAccount(UUID accountId, UUID likeId) {
        LikeList likeList = repository.findByAccountIdAndArticleId(accountId, likeId);
        if (likeList != null) repository.delete(likeList);
    }
}
