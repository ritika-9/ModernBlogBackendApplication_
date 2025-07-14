package com.ritika.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ritika.blog.entities.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    private Set<RoleDto> roles=new HashSet<>();


}
