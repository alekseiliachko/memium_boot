package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.enums.ERole;
import com.degenerates.memium.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getDefaultRole() {
        return roleRepository.findByName(ERole.ROLE_USER).orElseThrow(EntityNotFoundException::new);
    }

    public Set<Role> getDefaultRoleSet() {
        Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(EntityNotFoundException::new);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        return roleSet;
    }
}
