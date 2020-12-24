package com.degenerates.memium;

import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.enums.ERole;
import com.degenerates.memium.repository.RoleRepository;
import com.degenerates.memium.util.Populators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Populators populators;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {

        Role role = new Role(UUID.randomUUID(), ERole.ROLE_USER);
        if (!roleRepository.findByName(ERole.ROLE_USER).isPresent())
            roleRepository.save(role);

        log.info("Population started...");


//        populators.populateOne();
        populators.populateTwo();


        log.info("Population finished!");

    }
}
