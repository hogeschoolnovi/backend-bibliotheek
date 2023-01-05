package com.example.bibliotheek.controllers;

import com.example.bibliotheek.dtos.BookDto;
import com.example.bibliotheek.dtos.BookInputDto;
import com.example.bibliotheek.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getAllBooks(@RequestParam(value = "title",  required = false) Optional<String> title, @RequestParam(value = "initials", required = false) Optional<String> initials , @RequestParam(value = "name", required = false) Optional<String> name){
        if(title.isEmpty() && name.isPresent() && initials.isPresent()){
            return bookService.getAllBooksByAuthor(initials.get(), name.get());
        }else if (title.isPresent() && name.isEmpty() && initials.isEmpty()){
            return bookService.getAllBooksByTitle(title.get());
        }else {
            return bookService.getAllBooks();
        }
    }

    @GetMapping("/{isbn}")
    public BookDto getBook(@PathVariable String isbn){
        return bookService.getBook(isbn);
    }

    @PostMapping
    public BookDto saveBook(@RequestBody BookInputDto dto){
        return bookService.saveBook(dto);
    }

    @PutMapping("/{isbn}")
    public BookDto updateBook(@RequestBody BookInputDto dto, @PathVariable String isbn){
        return bookService.updateBook(dto, isbn);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn){
        bookService.deleteBook(isbn);
    }
}
