package com.degenerates.memium.repository;

import java.util.Optional;

import com.degenerates.memium.model.dao.ERole;
import com.degenerates.memium.model.dao.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
