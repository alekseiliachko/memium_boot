package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends MongoRepository<Article, UUID> {
    List<Article> findByAuthorId(UUID authorId);
}
