package com.blogapp.service.impl;

import com.blogapp.entity.Post;
import com.blogapp.exception.ResourceNotFound;
import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto){ // send details from dto to entity
        Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post); // .save helps to save data in db using jpa

        PostDto dto = mapToDto(savedPost); // send details from entity to dto

        return dto;
    }

    @Override
    public boolean deletePost(long id) {
        // Check if the post with the given ID exists before attempting to delete it
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true; // Return true if deletion was successful
        } else {
            return false; // Return false if the post with the given ID does not exist
        }
    }

    public PostDto getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id : " + id)
        );
        return mapToDto(post);
    }

    @Override
    public ListPostDto fetchAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> post = all.getContent();
        List<PostDto> postDtos = post.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        ListPostDto listPostDto = new ListPostDto();

        listPostDto.setPostDto(postDtos);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements((int) all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setPageNumber(all.getNumber());
        return listPostDto;
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
}
