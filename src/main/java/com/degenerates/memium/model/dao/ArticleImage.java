package com.degenerates.memium.model.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ArticleImage {

    @NonNull
    UUID articleId;

    @NonNull
    byte [] image;
}

