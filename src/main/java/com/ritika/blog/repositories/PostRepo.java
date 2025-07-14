package com.ritika.blog.repositories;

import com.ritika.blog.entities.Category;
import com.ritika.blog.entities.Post;
import com.ritika.blog.entities.User;
import com.ritika.blog.payloads.PostDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> searchByTitle(String title);


}
