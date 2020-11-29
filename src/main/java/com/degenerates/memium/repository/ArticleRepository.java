package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ArticleRepository extends MongoRepository<Article, UUID> {
}
