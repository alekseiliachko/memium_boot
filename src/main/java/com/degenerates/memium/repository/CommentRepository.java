package com.degenerates.memium.repository;

import com.degenerates.memium.model.dao.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends MongoRepository<Comment, UUID> {

    List<Comment> findByArticleId(UUID articleId);
    Comment findByAuthorId(UUID authorId);
}
