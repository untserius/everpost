package com.blogapp.controller;

import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import com.blogapp.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){

        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        if(postDto.getDescription().length()<5){
//            return new ResponseEntity<>("Description should be 5 characters or more", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
            if (postService.deletePost(id)) {
                return new ResponseEntity<>("Post is Deleted",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
            }
    }

    // http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=description&sortDir=asc
    @GetMapping
    public ResponseEntity<ListPostDto> fetchAllPosts(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        ListPostDto listPostDto = postService.fetchAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(listPostDto, HttpStatus.OK);
    }

    // http://localhost:8080/api/posts?id=1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
