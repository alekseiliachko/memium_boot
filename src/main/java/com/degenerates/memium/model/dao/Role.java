package com.degenerates.memium.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "roles")
public class Role {
    @Id
    private UUID id;

    @NonNull
    private ERole name;
}
