package com.example.bibliotheek.services;

import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Book;
import com.example.bibliotheek.models.Gender;
import com.example.bibliotheek.repoitories.AuthorRepository;
import com.example.bibliotheek.repoitories.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    BookService bookService;

    @Captor
    ArgumentCaptor captor;


    @BeforeEach
    void setUp() {
//        Author author1 = new Author("J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,07,31), Gender.FEMALE);
//        Book book1 = new Book("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", author1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void getAllBooks() {
        
    }

    @Test
    @Disabled
    void getAllBooksByTitle() {
    }

    @Test

    void getAllBooksByAuthor() {
        Author author1 = new Author("J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        Author author2 = new Author("R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);
        Book book1 = new Book("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", author1);
        Book book2 = new Book("9781781103470", "Harry Potter", "en de geheime kamer", "fiction", "NL", "paperback", "Pottermore publishing", author1);
        Book book3 = new Book("9789026139406", "Mathilda", "", "fiction", "NL", "paperback", "de Fontein Jeugd", author2);

        when(authorRepository.findAuthorByInitialsAndLastnameEquals("J.K. Rowling")).thenReturn(author1);
        when(bookRepository.findAllByAuthor(author1)).thenReturn(List.of(book1,book2));

        List<Book> booksFound = bookService.getAllBooksByAuthor("J.K. Rowling");

        assertEquals("J.K.", booksFound.get(0).getAuthor().getInitials());
        assertEquals("Rowling",  booksFound.get(0).getAuthor().getLastname());
        assertEquals(book1, booksFound.get(0));
        assertEquals(book2, booksFound.get(1));
    }
}