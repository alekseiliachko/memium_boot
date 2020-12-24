package com.degenerates.memium.model.dao;

import com.degenerates.memium.model.dto.CommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @NonNull
    UUID commendId;

    @NonNull
    UUID authorId;

    @NonNull
    UUID articleId;

    @NonNull
    Date date;

    @NonNull
    String content;

    public CommentDto toCommentDto() {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(commendId);
        commentDto.setArticleId(articleId);
        commentDto.setAuthorId(authorId);
        commentDto.setContent(content);
        commentDto.setDate(date);

        return commentDto;
    }
}
