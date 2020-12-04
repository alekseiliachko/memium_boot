package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public Article getById(UUID articleId) {
        return articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Article> getByAccountId(UUID accountId) {
        return articleRepository.findByAuthorId(accountId);
    }

    public Boolean checkIfExists(UUID articleId) {
        return articleRepository.existsById(articleId);
    }

    public Article save(Article article) {
        //todo
        return articleRepository.save(article);
    }

    public void deleteById(UUID articleId) {
        articleRepository.deleteById(articleId);
    }
}
