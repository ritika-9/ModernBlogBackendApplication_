package com.ritika.blog.services.impl;

import com.ritika.blog.config.AppConstant;
import com.ritika.blog.entities.Role;
import com.ritika.blog.entities.User;
import com.ritika.blog.exceptions.ResourceNotFoundException;
import com.ritika.blog.payloads.UserDto;
import com.ritika.blog.payloads.UserResponse;
import com.ritika.blog.repositories.*;
import com.ritika.blog.services.UserService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user1=this.dtoToUser(userDto);
        User savedUser=this.userRepo.save(user1);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        return null;
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        return this.userToUserResponse(user);
    }


    @Override
    public List<UserResponse> getAllUsers() {
        List<User> userList=this.userRepo.findAll();
        List<UserResponse> userResponseList=new ArrayList<UserResponse>();
        userList.forEach(user->{userResponseList.add(userToUserResponse(user));});
        return userResponseList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // break circular references if needed (defensive)
        user.getPosts().forEach(post -> post.setUser(null));
        user.getPosts().clear();
        user.getRoles().clear(); // break join table connection

        this.userRepo.delete(user);
    }



    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;

    }

    private UserDto userToUserDto(User user){

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return this.modelMapper.map(user, UserDto.class);
    }

    private UserResponse userToUserResponse(User user){
        return this.modelMapper.map(user, UserResponse.class);
    }
}
