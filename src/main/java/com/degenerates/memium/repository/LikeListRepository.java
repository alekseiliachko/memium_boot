package com.degenerates.memium.repository;

import com.degenerates.memium.model.relations.LikeList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeListRepository extends MongoRepository<LikeList, UUID> {
    List<LikeList> findByAccountId(UUID accountId);

    List<LikeList> findByArticleId(UUID accountId);

    Long countByArticleId(UUID accountId);

    LikeList findByAccountIdAndArticleId(UUID accountId, UUID articleId);

    Boolean existsByAccountIdAndArticleId(UUID accountId, UUID articleId);

    void deleteByArticleId(UUID articleId);

    void deleteByArticleIdAndAccountId(UUID articleId, UUID accountId);
}