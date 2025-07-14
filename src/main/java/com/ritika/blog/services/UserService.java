package com.ritika.blog.services;

import com.ritika.blog.payloads.UserDto;
import com.ritika.blog.payloads.UserResponse;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer id);
    UserResponse getUserById(Integer id);
    List<UserResponse> getAllUsers();
    void deleteUser(Integer id);
}
