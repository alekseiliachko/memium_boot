package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;


    public Comment getById(UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Comment> getByArticleId(UUID articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteByAtricleId(UUID articleId) {
        deleteByAtricleId(articleId);
    }

    public void deleteById(UUID commentId) {
        commentRepository.deleteById(commentId);
    }
}
