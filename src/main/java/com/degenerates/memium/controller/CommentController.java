package com.degenerates.memium.controller;

import com.degenerates.memium.facade.CommentFacade;
import com.degenerates.memium.model.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment/")
public class CommentController {

    @Autowired
    CommentFacade commentFacade;

    @PostMapping
    public ResponseEntity<?> comment(@RequestHeader HttpHeaders headers,  @RequestBody CommentDto commentDto) {
        String token = headers.get(TOKEN_VAR).get(0);

        return commentFacade.createComment(commentDto, token);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable UUID commentId) {
        String token = headers.get(TOKEN_VAR).get(0);

        return commentFacade.deleteComment(commentId, token);
    }
}
