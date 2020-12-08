package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends MongoRepository<Image, UUID> {

    Optional<Image> findByAccountId(UUID accountId);
}
