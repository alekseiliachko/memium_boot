package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Account, UUID> {

    Account findByUsername(String username);
}
