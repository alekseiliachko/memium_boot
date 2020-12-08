package com.degenerates.memium.service;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.model.dao.Image;
import com.degenerates.memium.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Image getByAccountId(UUID accountId) {
        return imageRepository.findByAccountId(accountId).orElseThrow(EntityNotFoundException::new);
    }
}
