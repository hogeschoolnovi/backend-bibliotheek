package com.example.bibliotheek.controllers;

import com.example.bibliotheek.models.Book;
import com.example.bibliotheek.services.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String name){
        if(title.isEmpty() && !name.isEmpty()){
            return bookService.getAllBooksByAuthor(name);
        }else if (!title.isEmpty() && name.isEmpty()){
            return bookService.getAllBooksByTitle(title);
        }else {
            return bookService.getAllBooks();
        }
    }
}
