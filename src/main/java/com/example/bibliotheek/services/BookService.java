package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.BookDto;
import com.example.bibliotheek.dtos.BookInputDto;
import com.example.bibliotheek.exceptions.AuthorNotFoundException;
import com.example.bibliotheek.exceptions.RecordNotFoundException;
import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Book;
import com.example.bibliotheek.repoitories.AuthorRepository;
import com.example.bibliotheek.repoitories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final AuthorService authorService;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       AuthorService authorService){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    public List<BookDto> getAllBooks(){
        return transferBookListToBookDtoList(bookRepository.findAll());
    }

    public List<BookDto> getAllBooksByTitle(String title){
        return transferBookListToBookDtoList(bookRepository.findAllByTitle(title));
    }

    public List<BookDto> getAllBooksByAuthor(String initials, String name){
        Author author = authorRepository.findAuthorByInitialsAndLastnameEqualsIgnoreCase(initials, name);
        return transferBookListToBookDtoList(bookRepository.findAllByAuthor(author));
    }

    public BookDto getBook(String isbn){
        if(bookRepository.findById(isbn).isPresent()){
            return transferBookToBookDto(bookRepository.findById(isbn).get());
        } else{
            throw new RecordNotFoundException("There is no book known by isbn: " + isbn);
        }

    }

    public BookDto saveBook(BookInputDto dto){
        if (authorRepository.findById(dto.id()).isPresent()) {
            Book newBook = new Book(dto.isbn(), dto.title(), dto.subtitle(), dto.genre(), dto.language(), dto.type(), dto.publisher(), authorRepository.findById(dto.id()).get());
            Book book = bookRepository.save(newBook);
            return transferBookToBookDto(book);
        }else {
            throw new AuthorNotFoundException("No author known by this id");
        }
    }

    public BookDto updateBook(BookInputDto dto, String isbn){
        if(bookRepository.findById(isbn).isPresent()){
            Book book = bookRepository.findById(isbn).get();
            if(authorRepository.findById(dto.id()).isPresent()){
            if(!book.getAuthor().equals(authorRepository.findById(dto.id()).get())){
                book.setAuthor(authorRepository.findById(dto.id()).get());
            }}else {
                throw new AuthorNotFoundException("No author found by this id");
            }
            if(!book.getGenre().equals(dto.genre())){
                book.setGenre(dto.genre());
            }
            if(!book.getLanguage().equals(dto.language())){
                book.setLanguage(dto.language());
            }
            if(!book.getPublisher().equals(dto.publisher())){
                book.setPublisher(dto.publisher());
            }
            if(!book.getSubtitle().equals(dto.subtitle())){
                book.setSubtitle(dto.subtitle());
            }
            if(!book.getTitle().equals(dto.title())){
                book.setTitle(dto.title());
            }
            if(!book.getType().equals(dto.type())){
                book.setType(dto.type());
            }
            return (transferBookToBookDto(bookRepository.save(book)));

        }else {
            throw new RecordNotFoundException("There is no book known by this isbn " + isbn);
        }
    }

    public void deleteBook(String isbn){
        bookRepository.deleteById(isbn);
    }


    public Book transferBookDtoToBook(BookDto dto){
        return (new Book(dto.isbn(), dto.title(), dto.subtitle(), dto.genre(), dto.language(), dto.type(), dto.publisher(), authorService.transferAuthorDtoToAuthor(dto.authorDto())));
    }

    public BookDto transferBookToBookDto(Book book){
        return (new BookDto(book.getIsbn(), book.getTitle(), book.getSubtitle(), book.getGenre(), book.getLanguage(), book.getType(), book.getPublisher(), authorService.transferAuthorToAuthorDto(book.getAuthor())));
    }

    public List<BookDto> transferBookListToBookDtoList(List<Book> books){
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {

            bookDtos.add(transferBookToBookDto(book));
        }
        return bookDtos;
    }

    public List<Book> transferBookDtoListToBookList(List<BookDto> bookDtos){
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(transferBookDtoToBook(bookDto));
        }
        return books;
    }
}
