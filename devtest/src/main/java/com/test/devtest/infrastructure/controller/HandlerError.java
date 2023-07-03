package com.test.devtest.infrastructure.controller;

import com.test.devtest.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HandlerError {

    public ResponseEntity ValidateException(Exception e){
         if(e instanceof NotFoundException){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
