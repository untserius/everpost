package com.blogapp.service;

import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    boolean deletePost(long id);

    ListPostDto fetchAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto getPostById(long id);
}
