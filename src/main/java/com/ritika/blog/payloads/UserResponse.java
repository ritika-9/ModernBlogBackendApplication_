package com.ritika.blog.payloads;

import lombok.Data;

@Data
public class UserResponse {

    private int id;
    private String name;
    private String email;
    private String about;
    // Optional (if you're implementing roles or timestamps)
     private String role;
    // private LocalDateTime createdAt;
}
