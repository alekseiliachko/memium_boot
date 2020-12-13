package com.degenerates.memium.controller;

import com.degenerates.memium.facade.CommentFacade;
import com.degenerates.memium.model.dto.CommentDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/comment")
public class CommentOpenController {

    @Autowired
    CommentFacade commentFacade;

    @ApiOperation(value = "Get comments by articleId provided ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/{articleId}")
    public ResponseEntity<List<CommentDto>> getCommentsForArticle(@PathVariable UUID articleId) {
        return commentFacade.getCommentsForArticle(articleId);
    }
}
