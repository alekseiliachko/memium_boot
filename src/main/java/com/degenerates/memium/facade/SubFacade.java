package com.degenerates.memium.facade;

import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.service.AccountShortService;
import com.degenerates.memium.service.SubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SubFacade {

    @Autowired
    SubService subService;

    @Autowired
    AccountShortService accountShortService;

    public ResponseEntity<List<AccountShortDto>> getSubOfAccount(UUID accountId) {
        List<UUID> accountIds = subService.getAccountSubbedBy(accountId);
        List<AccountShortDto> accountShortDtoList = accountShortService.getAccountsByIds(accountIds);

        log.info("Found " + accountShortDtoList.size() + " accounts subbed by: " + accountId);

        return ResponseEntity.ok(accountShortDtoList);
    }
}
