package com.ritika.blog.services;

import com.ritika.blog.entities.Category;
import com.ritika.blog.entities.Post;
import com.ritika.blog.payloads.PostDto;

import java.util.List;

public interface PostService {
    public Post createPost(PostDto postDto,Integer categoryId,Integer userId);

    public Post updatePost(PostDto postDto,Integer postId);

    public void deletePost(Integer postId);

    public List<Post> getAllPosts();

    public PostDto getPostById(Integer postId);

    public List<Post> getPostsByCategory(Integer categoryId);

    public List<Post> getPostsByUser(Integer userId);

    public List<Post> searchPostsByTitle(String title);
}
