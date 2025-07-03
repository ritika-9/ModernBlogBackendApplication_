package com.ritika.blog.exceptions;

import com.ritika.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //for rest ones
public class GlobalExceptionHandler { //this will catch all

    @ExceptionHandler(ResourceNotFoundException.class) //enter class of exception you want to handle here
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
         String message = e.getMessage();
         ApiResponse apiResponse=new ApiResponse(message,false);
         return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgsNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String,String> resp=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
}
