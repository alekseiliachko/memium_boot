package com.degenerates.memium.model.dto;

import com.degenerates.memium.model.dao.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    UUID id;
    UUID authorId;
    UUID articleId;
    String content;
    Date date;


    public Comment toComment() {
        Comment comment = new Comment();

        comment.setCommendId(id);
        comment.setArticleId(articleId);
        comment.setAuthorId(authorId);
        comment.setContent(content);
        comment.setDate(date);

        return comment;
    }
}
