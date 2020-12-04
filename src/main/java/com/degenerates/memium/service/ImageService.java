package com.degenerates.memium.service;

import com.degenerates.memium.model.dao.Image;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageService {

    public Image getByAccountId(UUID accountId) {
        Image image = new Image();
        image.setAccountId(accountId);
        return image;
    }
}
