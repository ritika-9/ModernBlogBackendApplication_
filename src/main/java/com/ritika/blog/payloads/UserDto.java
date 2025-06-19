package com.ritika.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 3, max = 20,message = "Username should be greater than 2 characters!")
    private String name;

    @Email(message = "email cannot be empty!")
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min=3,message = "Password must be greater then 2 characters!")
    private String password;

    @NotEmpty
    private String about;


}
