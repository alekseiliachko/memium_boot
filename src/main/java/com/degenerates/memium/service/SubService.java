package com.degenerates.memium.service;

import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.repository.SubListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubService {

    @Autowired
    SubListRepository repository;

    public List<UUID> getAccountSubbedBy(UUID accountId) {
        return repository.findByAccountId(accountId)
                .stream().map(SubList::getSubId)
                .collect(Collectors.toList());
    };

    public void byAccountSubToAccount(UUID accountId, UUID subId) {
        SubList subList = new SubList(UUID.randomUUID(), accountId, subId);
        if (!repository.existsByAccountIdAndSubId(accountId, subId))
            repository.save(subList);
    }

    public void byAccountUnsubAccount(UUID accountId, UUID subId) {
        SubList subList = repository.findByAccountIdAndSubId(accountId, subId);
        if (subList != null) repository.delete(subList);
    }
}