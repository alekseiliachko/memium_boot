package com.degenerates.memium.repository;

import com.degenerates.memium.model.relations.SubList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubListRepository extends MongoRepository<SubList, UUID> {
    List<SubList> findByAccountId(UUID accountId);

    SubList findByAccountIdAndSubId(UUID accountId, UUID subId);

    Boolean existsByAccountIdAndSubId(UUID accountId, UUID subId);
}
