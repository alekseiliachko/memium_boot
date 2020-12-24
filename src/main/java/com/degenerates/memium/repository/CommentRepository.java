package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends MongoRepository<Comment, UUID> {

    List<Comment> findByArticleId(UUID articleId);

    void deleteByArticleId(UUID articleId);
}
