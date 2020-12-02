package com.degenerates.memium.service;

import com.degenerates.memium.model.relations.BlackList;
import com.degenerates.memium.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlackListService {

    @Autowired
    BlackListRepository repository;

    public List<BlackList> getAccountData(UUID accountId) {
        return repository.findByAccountId(accountId);
    };

    public void byAccountBlockAccount(UUID accountId, UUID blockId) {
        BlackList blackList = new BlackList(accountId, blockId);
        if (!repository.existsByAccountIdAndBlockedId(accountId, blockId))
            repository.save(blackList);
    }

    public void byAccountUnblockAccount(UUID accountId, UUID blockedId) {
        BlackList blackList = repository.findByAccountIdAndBlockedId(accountId, blockedId);
        if (blackList != null) repository.delete(blackList);
    }
}