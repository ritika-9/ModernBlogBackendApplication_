package com.ritika.blog.payloads;

import com.ritika.blog.entities.Category;
import com.ritika.blog.entities.Comment;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.*;

@Data
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;
    private CategoryDto category;
    private UserDto user;


    private List<CommentDto> comments=new ArrayList<>();


}
