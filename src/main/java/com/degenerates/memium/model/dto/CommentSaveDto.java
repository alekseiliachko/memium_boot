package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.dao.Comment;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CommentSaveDto {

    UUID articleId;
    String content;


    public Comment toComment() {
        Comment comment = new Comment();

        comment.setArticleId(articleId);
        comment.setContent(content);

        return comment;
    }
}
