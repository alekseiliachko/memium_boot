package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Account, UUID> {

    Account findByUsername(String username);

    Boolean existsByUsername(String username);

    List<Account> findByAccountId(List<UUID> accountIds);

    List<Account> findByUsernameRegex(String username);
}
