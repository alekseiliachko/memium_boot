package com.degenerates.memium.facade;

import com.degenerates.memium.model.dto.AccountShortDto;
import com.degenerates.memium.service.AccountShortService;
import com.degenerates.memium.service.SubService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubFacadeTest {

    @Mock
    SubService subService;

    @Mock
    AccountShortService accountShortService;

    @InjectMocks
    SubFacade subFacade;

    private static final UUID accountId = UUID.randomUUID();

    void getSubOfAccount() {

        List<UUID> list = Lists.list(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        when(subService.getAccountSubbedBy(accountId)).thenReturn(list);
        when(accountShortService.getAccountsByIds(list)).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), subFacade.getSubOfAccount(accountId).getBody());
    }
}
