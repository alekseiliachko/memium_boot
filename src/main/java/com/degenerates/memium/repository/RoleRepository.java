package com.degenerates.memium.repository;

import java.util.Optional;

import com.degenerates.memium.model.enums.ERole;
import com.degenerates.memium.model.dao.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
