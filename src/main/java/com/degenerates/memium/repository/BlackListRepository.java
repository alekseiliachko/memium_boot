package com.degenerates.memium.repository;

import com.degenerates.memium.model.relations.BlackList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlackListRepository extends MongoRepository<BlackList, UUID> {
    List<BlackList> findByAccountId(UUID accountId);

    BlackList findByAccountIdAndBlockedId(UUID accountId, UUID blockedId);

    Boolean existsByAccountIdAndBlockedId(UUID accountId, UUID blockedId);
}
