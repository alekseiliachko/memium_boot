package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
