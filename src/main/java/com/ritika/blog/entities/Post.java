package com.ritika.blog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @NotBlank
    @Size(min = 5, max = 50)
    private String title;

    private String content;

    private String imageName;

    private Date addDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;


}
