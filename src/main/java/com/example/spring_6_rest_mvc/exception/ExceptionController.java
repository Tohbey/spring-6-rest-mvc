package com.example.spring_6_rest_mvc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){

        System.out.println("In exception Handler");
        return ResponseEntity.notFound().build();
    }

}
