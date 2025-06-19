package com.ritika.blog.exceptions;

import com.ritika.blog.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //for rest ones
public class GlobalExceptionHandler { //this will catch all

    @ExceptionHandler(ResourceNotFoundException.class) //enter class of exception you want to handle here
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
         String message = e.getMessage();


    }
}
