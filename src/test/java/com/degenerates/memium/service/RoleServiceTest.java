package com.degenerates.memium.service;

import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.enums.ERole;
import com.degenerates.memium.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    private static final Optional<Role> role = Optional.of(new Role(UUID.randomUUID(), ERole.ROLE_USER));
    private static final Set<Role> roleSet = Sets.newSet(role.get());

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @Test
    void testFound() {
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(role);
        assertEquals(ERole.ROLE_USER, roleService.getDefaultRole().getName());
    }

    @Test
    void testFoundSet() {
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(role);
        assertEquals(roleSet, roleService.getDefaultRoleSet());
    }

}
