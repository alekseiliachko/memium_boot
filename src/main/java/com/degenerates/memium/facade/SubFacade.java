package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.model.relations.SubList;
import com.degenerates.memium.service.AccountDetailsService;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.ImageService;
import com.degenerates.memium.service.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SubFacade {

    @Autowired
    SubService subService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailsService accountDetailsService;

    @Autowired
    ImageService imageService;

    private List<AccountShortDto> getAccountShortDtoFromList(List<UUID> list) {
        List<Account> accountList = accountService.getByIds(list);
        List<AccountDetails> accountDetailsList = accountDetailsService.getByAccountIds(list);
        List<AccountShortDto> accountShortDtoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AccountShortDto accountShortDto = new AccountShortDto();
            accountShortDto.setBio(accountDetailsList.get(i).getBio());
            accountShortDto.setName(accountDetailsList.get(i).getName());
            accountShortDto.setUsername(accountList.get(i).getUsername());
            accountShortDto.setImageData(imageService.getByAccountId(list.get(i)).getImage());
            accountShortDtoList.add(accountShortDto);
        }
        return accountShortDtoList;
    }

    public ResponseEntity<List<AccountShortDto>> getSubOfAccount(UUID accountId) {
        List<AccountShortDto> list = getAccountShortDtoFromList(subService.getAccountData(accountId).stream().map(SubList::getSubId).collect(Collectors.toList()));

        return ResponseEntity.ok(list);
    }
}
