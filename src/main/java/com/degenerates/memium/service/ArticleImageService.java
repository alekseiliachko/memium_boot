package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.exceptions.OptionalEntityNotFoundException;
import com.degenerates.memium.model.dao.ArticleImage;
import com.degenerates.memium.repository.ArticleImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArticleImageService {

    @Autowired
    ArticleImageRepository articleImageRepository;

    public ArticleImage save(ArticleImage articleImage) {
        return articleImageRepository.save(articleImage);
    }

    public ArticleImage getByArticleId(UUID articleId) {
        return articleImageRepository.findByArticleId(articleId).orElseThrow(OptionalEntityNotFoundException::new);
    }

    public boolean checkIfAlreadySet(UUID articleId) {
        return articleImageRepository.existsByArticleId(articleId);
    }

    public void deleteByArticleId(UUID articleId) {
        articleImageRepository.deleteByArticleId(articleId);
    }
}