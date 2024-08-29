package com.blogapp.payload;

import lombok.Data;

import java.util.List;
@Data
public class ListPostDto {
    private List<PostDto> postDto;
    private int totalPages;
    private int totalElements;
    private boolean lastPage;
    private boolean firstPage;
    private int pageNumber;
}
