package com.ritika.blog.repositories;

import com.ritika.blog.entities.Category;
import com.ritika.blog.entities.Post;
import com.ritika.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    public List<Post> findByUser(User user);
    public List<Post> findByCategory(Category category);

}
