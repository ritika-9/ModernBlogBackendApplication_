package com.ritika.blog.services.impl;

import com.ritika.blog.entities.Post;
import com.ritika.blog.entities.User;
import com.ritika.blog.exceptions.ResourceNotFoundException;
import com.ritika.blog.payloads.PostDto;
import com.ritika.blog.repositories.CategoryRepo;
import com.ritika.blog.repositories.UserRepo;
import com.ritika.blog.services.PostService;
import com.ritika.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public Post createPost(PostDto postDto, Integer categoryId, Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Post","id",userId));

        Post post=this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());


        return null;
    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<Post> getAllPosts() {
        return List.of();
    }

    @Override
    public PostDto getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Integer categoryId) {
        return List.of();
    }

    @Override
    public List<Post> getPostsByUser(Integer userId) {
        return List.of();
    }

    @Override
    public List<Post> searchPostsByTitle(String title) {
        return List.of();
    }
}
