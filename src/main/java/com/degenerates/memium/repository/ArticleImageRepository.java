package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.ArticleImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleImageRepository extends MongoRepository<ArticleImage, UUID> {

    Optional<ArticleImage> findByArticleId(UUID articleId);

    Boolean existsByArticleId(UUID articleId);

    void deleteByArticleId(UUID articleId);
}