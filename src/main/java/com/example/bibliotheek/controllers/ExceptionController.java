package com.example.bibliotheek.controllers;

import com.example.bibliotheek.exceptions.AuthorNotFoundException;
import com.example.bibliotheek.exceptions.RecordNotFoundException;
import com.example.bibliotheek.models.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = AuthorNotFoundException.class)
    public ResponseEntity<Object> exception(AuthorNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

}