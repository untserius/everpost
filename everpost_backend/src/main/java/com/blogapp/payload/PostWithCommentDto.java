package com.blogapp.payload;

import com.blogapp.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostWithCommentDto {

    private PostDto post;
    private List<CommentDto> commentDto;
}
