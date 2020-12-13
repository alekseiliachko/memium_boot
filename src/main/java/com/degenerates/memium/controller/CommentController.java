package com.degenerates.memium.controller;

import com.degenerates.memium.facade.CommentFacade;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.CommentDto;
import com.degenerates.memium.model.dto.CommentSaveDto;
import com.degenerates.memium.util.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Create comment authored by token in Headers ", response = CommentDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping
    public ResponseEntity<CommentDto> comment(@RequestHeader HttpHeaders headers,  @RequestBody CommentSaveDto commentSaveDto) {
        return commentFacade.createComment(commentSaveDto, utils.extractToken(headers));
    }

    @ApiOperation(value = "Delete comment authored by token in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @DeleteMapping("/{commentId}")
    public HttpStatus deleteComment(@RequestHeader HttpHeaders headers, @PathVariable UUID commentId) {
        return commentFacade.deleteComment(commentId, utils.extractToken(headers));
    }
}
