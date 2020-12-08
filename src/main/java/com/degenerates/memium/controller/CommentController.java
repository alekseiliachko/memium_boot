package com.degenerates.memium.controller;

import com.degenerates.memium.facade.CommentFacade;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Autowired
    Utils utils;

    @PostMapping
    public ResponseEntity<CommentDto> comment(@RequestHeader HttpHeaders headers,  @RequestBody CommentDto commentDto) {
        return commentFacade.createComment(commentDto, utils.extractToken(headers));
    }


    @DeleteMapping("/{commentId}")
    public HttpStatus deleteComment(@RequestHeader HttpHeaders headers, @PathVariable UUID commentId) {
        return commentFacade.deleteComment(commentId, utils.extractToken(headers));
    }
}
