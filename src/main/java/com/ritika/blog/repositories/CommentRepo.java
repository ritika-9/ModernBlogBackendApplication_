package com.ritika.blog.repositories;

import com.ritika.blog.entities.Comment;
import com.ritika.blog.entities.Post;
import com.ritika.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findByPost(Post post);


}
