package com.ritika.blog.services.impl;

import com.ritika.blog.entities.User;
import com.ritika.blog.exceptions.ResourceNotFoundException;
import com.ritika.blog.payloads.UserDto;
import com.ritika.blog.repositories.*;
import com.ritika.blog.services.UserService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto getUserById(Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        return this.userToUserDto(user);
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList=this.userRepo.findAll();
        List<UserDto> userDtoList=new ArrayList<UserDto>();
        userList.forEach(user->{userDtoList.add(userToUserDto(user));});
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
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
}
