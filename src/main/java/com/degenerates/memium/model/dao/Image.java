package com.degenerates.memium.model.dao;

import lombok.NonNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Data
@RequiredArgsConstructor
public class Image {

    @NonNull
    UUID accountId;

    @NonNull
    byte [] image;
}
