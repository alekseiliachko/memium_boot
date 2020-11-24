package com.degenerates.memium.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Article {

    @Id
    @NonNull
    UUID articleId;

    @NonNull
    UUID authorId;

    @NonNull
    String title;

    @NonNull
    Date date;

    @NonNull
    Category category;
}
