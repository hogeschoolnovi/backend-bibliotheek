package com.example.bibliotheek.services;

import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Book;
import com.example.bibliotheek.repoitories.AuthorRepository;
import com.example.bibliotheek.repoitories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByTitle(String title){
        return bookRepository.findAllByTitle(title);
    }

    public List<Book> getAllBooksByAuthor(String name){
        Author author = authorRepository.findAuthorByInitialsAndLastnameEqualsIgnoreCase(name);
        return bookRepository.findAllByAuthor(author);
    }
}
