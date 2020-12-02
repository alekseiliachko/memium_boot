package com.degenerates.memium.model.relations;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class LikeList {
    @NonNull
    UUID accountId;
    @NonNull
    UUID articleId;
}
