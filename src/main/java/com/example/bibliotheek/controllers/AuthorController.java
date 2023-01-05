package com.example.bibliotheek.controllers;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.services.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id){
        return authorService.getAuthor(id);
    }

    @PostMapping
    public AuthorDto saveAuthor(@RequestBody AuthorDto dto){
        return authorService.saveAuthor(dto);
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@RequestBody AuthorDto dto, @PathVariable Long id){
        return authorService.updateAuthor(dto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
    }
}
