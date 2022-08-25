package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.BookDto;
import com.example.bibliotheek.dtos.BookInputDto;
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
        Book newBook = new Book(dto.getIsbn(), dto.getTitle(), dto.getSubtitle(), dto.getGenre(), dto.getLanguage(), dto.getType(), dto.getPublisher(), authorRepository.findById(dto.getUuid()).get());
        Book book = bookRepository.save(newBook);
        return transferBookToBookDto(book);
    }

    public BookDto updateBook(BookDto dto, String isbn){
        if(bookRepository.findById(isbn).isPresent()){
            Book book = bookRepository.findById(isbn).get();
            if(!book.getAuthor().equals(authorService.transferAuthorDtoToAuthor(dto.getAuthorDto()))){
                book.setAuthor(authorService.transferAuthorDtoToAuthor(dto.getAuthorDto()));
            }
            if(!book.getGenre().equals(dto.getGenre())){
                book.setGenre(dto.getGenre());
            }
            if(!book.getLanguage().equals(dto.getLanguage())){
                book.setLanguage(dto.getLanguage());
            }
            if(!book.getPublisher().equals(dto.getPublisher())){
                book.setPublisher(dto.getPublisher());
            }
            if(!book.getSubtitle().equals(dto.getSubtitle())){
                book.setSubtitle(dto.getSubtitle());
            }
            if(!book.getTitle().equals(dto.getTitle())){
                book.setTitle(dto.getTitle());
            }
            if(!book.getType().equals(dto.getType())){
                book.setType(dto.getType());
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
        return (new Book(dto.getIsbn(), dto.getTitle(), dto.getSubtitle(), dto.getGenre(), dto.getLanguage(), dto.getType(), dto.getPublisher(), authorService.transferAuthorDtoToAuthor(dto.getAuthorDto())));
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
