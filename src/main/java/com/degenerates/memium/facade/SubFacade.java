package com.degenerates.memium.facade;

import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.service.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SubFacade {

    @Autowired
    SubService subService;

    public ResponseEntity<?> getSubOfAccount(UUID accountId) {
        return ResponseEntity.ok(subService.getAccountData(accountId).stream().map(SubList::getSubId).collect(Collectors.toList()));
    }
}
