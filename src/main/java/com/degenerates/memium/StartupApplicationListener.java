package com.degenerates.memium;

import com.degenerates.memium.model.dao.ERole;
import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    public static int counter;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {

        counter++;
    }
}
