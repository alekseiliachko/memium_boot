package com.degenerates.memium.controller;

import com.degenerates.memium.facade.ArticleFacade;
import com.degenerates.memium.model.dao.AccountImage;
import com.degenerates.memium.model.dto.AccountDto;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.ArticleSaveDto;
import com.degenerates.memium.model.dto.ArticleShortDto;
import com.degenerates.memium.service.ArticleImageService;
import com.degenerates.memium.util.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.degenerates.memium.util.Constants.TOKEN_VAR;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/article/")
public class ArticleController {

    @Autowired
    ArticleFacade articleFacade;

    @Autowired
    Utils utils;


    @ApiOperation(value = "Get Short Articles by token provided in Headers ", response = Iterable.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @GetMapping("/my")
    public ResponseEntity<List<ArticleShortDto>> getMyArticles(@RequestHeader HttpHeaders headers) {
        return articleFacade.getMyArticles(utils.extractToken(headers));
    }


    @ApiOperation(value = "Create Article authored by token provided in Headers ", response = ArticleDto.class, produces = "application/json")
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
    public ResponseEntity<ArticleDto> createArticle(@RequestHeader HttpHeaders headers, @RequestBody ArticleSaveDto articleSaveDto) {
        return articleFacade.createArticle(articleSaveDto, utils.extractToken(headers));
    }

    @ApiOperation(value = "Set Image for articleId ", response = byte[].class, produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PostMapping("/image/{articleId}")
    public byte[] setArticleImage(@RequestHeader HttpHeaders headers, @RequestParam("image") MultipartFile image, @PathVariable UUID articleId) {
        return articleFacade.setArticleImage(utils.extractToken(headers),articleId, image);
    }

    @ApiOperation(value = "Update Article authored by token provided in Headers ", response = ArticleDto.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @PutMapping
    public ResponseEntity<ArticleDto> updateArticle(@RequestHeader HttpHeaders headers, @RequestBody ArticleDto articleDto) {
        return articleFacade.updateArticle(articleDto, utils.extractToken(headers));
    }

    @ApiOperation(value = "Delete Article authored by token provided in Headers ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content fo show"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 405, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Bad Media File provided"),
    })
    @DeleteMapping("/{articleId}")
    public HttpStatus deleteArticle(@RequestHeader HttpHeaders headers, @PathVariable UUID articleId) {
        return articleFacade.deleteArticle(articleId, utils.extractToken(headers));
    }
}
