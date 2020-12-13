package com.degenerates.memium.model.relations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BlackList {

    @Id
    UUID id;

    @NonNull
    UUID accountId;

    @NonNull
    UUID blockedId;
}
