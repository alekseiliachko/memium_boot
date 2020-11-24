package com.degenerates.memium.model.dao;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Comment {

    @Id
    @NonNull
    UUID commendId;

    @NonNull
    Date date;

    @NonNull
    String content;
}
