package com.ritika.blog.controllers;

import com.ritika.blog.exceptions.ApiException;
import com.ritika.blog.payloads.JwtAuthRequest;
import com.ritika.blog.payloads.JwtAuthResponse;
import com.ritika.blog.payloads.UserDto;
import com.ritika.blog.security.JwtTokenHelper;
import com.ritika.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createtoken(
            @RequestBody JwtAuthRequest request)
    {
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
        String token=this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);

        try{
            this.authenticationManager.authenticate(auth);
        }catch(BadCredentialsException e){
            System.out.println("Bad credentials");
            throw new ApiException("Bad credentials");
        }


    }

    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto registeredUser =this.userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }



}
