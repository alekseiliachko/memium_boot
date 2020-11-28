package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.AccountDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AccountDetailsRepository extends MongoRepository<AccountDetails, UUID> {

    AccountDetails findByAccountId(UUID id);
}
