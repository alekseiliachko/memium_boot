package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.AccountImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountImageRepository extends MongoRepository<AccountImage, UUID> {

    Optional<AccountImage> findByAccountId(UUID accountId);

    Boolean existsByAccountId(UUID accountId);

    void deleteByAccountId(UUID accountId);
}
