package com.degenerates.memium.model.dao;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Image {

    @NonNull
    UUID accountId;

    @NonNull
    byte [] image;
}
