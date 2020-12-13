package com.degenerates.memium.controller;

import com.degenerates.memium.facade.ArticleFacade;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open/article/")
public class ArticleOpenController {

    @Autowired
    ArticleFacade articleFacade;

    @ApiOperation(value = "Get Short Articles by AccountId ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/accountId/{accountId}")
    public ResponseEntity<List<ArticleShortDto>> getArticlesByAccountId(@PathVariable UUID accountId) {
        return articleFacade.getArticlesForAccountId(accountId);
    }

    @ApiOperation(value = "Get Article Image by AccountId ", response = byte[].class, produces = "application/application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping("/image/{articleId}")
    public byte[] getArticleImageByArticleId(@RequestHeader HttpHeaders headers, @RequestParam("image") MultipartFile image, @PathVariable UUID articleId) {
        return articleFacade.getArticleImage(articleId);
    }

    @ApiOperation(value = "Get Short Article by ArticleId ", response = ArticleDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/articleId/{articleId}")
    public ResponseEntity<ArticleDto> getArticleByArticleId(@PathVariable UUID articleId) {
        return articleFacade.getArticle(articleId);
    }
}
