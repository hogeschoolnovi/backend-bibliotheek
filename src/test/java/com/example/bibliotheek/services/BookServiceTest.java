package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.BookDto;
import com.example.bibliotheek.dtos.BookInputDto;
import com.example.bibliotheek.exceptions.AuthorNotFoundException;
import com.example.bibliotheek.exceptions.RecordNotFoundException;
import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Book;
import com.example.bibliotheek.models.Gender;
import com.example.bibliotheek.repoitories.AuthorRepository;
import com.example.bibliotheek.repoitories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorService authorService;

    @InjectMocks
    BookService bookService ;

    @Captor
    ArgumentCaptor<Book> captor;

    Author author1;
    Author author2;
    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    void setUp() {
        author1 = new Author(1L ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        author2 = new Author(2L,"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);
        book1 = new Book("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", author1);
        book2 = new Book("9781781103470", "Harry Potter", "en de geheime kamer", "fiction", "NL", "paperback", "Pottermore publishing", author1);
        book3 = new Book("9789026139406", "Mathilda", "", "fiction", "NL", "paperback", "de Fontein Jeugd", author2);
    }

    @Test
    void getAllBooks() {
            when(bookRepository.findAll()).thenReturn(List.of(book1,book2));

            List<Book> booksFound = bookService.transferBookDtoListToBookList(bookService.getAllBooks());

            assertEquals(book1.getTitle(), booksFound.get(0).getTitle());
            assertEquals(book2.getTitle(), booksFound.get(1).getTitle());

    }

    @Test
    void getAllBooksByTitle() {
        when(bookRepository.findAllByTitle("Harry Potter")).thenReturn(List.of(book1,book2));

        List<BookDto> booksFound = bookService.getAllBooksByTitle("Harry Potter");

        assertEquals(book1.getTitle(), booksFound.get(0).title());
        assertEquals(book2.getTitle(), booksFound.get(1).title());
    }

    @Test
    void getAllBooksByAuthor() {
        when(authorRepository.findAuthorByInitialsAndLastnameEqualsIgnoreCase("J.K.", "Rowling")).thenReturn(author1);
        when(bookRepository.findAllByAuthor(author1)).thenReturn(List.of(book1,book2));

        List<BookDto> booksFound = bookService.getAllBooksByAuthor("J.K.", "Rowling");

        assertEquals(book1.getTitle(), booksFound.get(0).title());
        assertEquals(book2.getTitle(), booksFound.get(1).title());
    }

    @Test
    void getBookTest() {
        when(bookRepository.findById("9789076174105")).thenReturn(Optional.of(book1));

        BookDto dto = bookService.getBook("9789076174105");

        assertEquals(book1.getTitle(), dto.title());
    }

    @Test
    void getBookThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> bookService.getBook(null));
    }

    @Test
    void saveBook() {
        BookInputDto dto = new BookInputDto("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", 1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(bookRepository.save(book1)).thenReturn(book1);

        bookService.saveBook(dto);
        verify(bookRepository, times(1)).save(captor.capture());
        Book book = captor.getValue();

        assertEquals(book1.getIsbn(), book.getIsbn());
        assertEquals(book1.getTitle(), book.getTitle());
        assertEquals(book1.getType(), book.getType());
        assertEquals(book1.getSubtitle(), book.getSubtitle());
        assertEquals(book1.getPublisher(), book.getPublisher());
        assertEquals(book1.getGenre(), book.getGenre());
    }

    @Test
    void saveBookThrowsExceptionTest() {
        assertThrows(AuthorNotFoundException.class, () -> bookService.saveBook(new BookInputDto("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", 1L)));
    }
    @Test
    void updateBook() {
        BookInputDto bookInputDto = new BookInputDto("9789076174105" ,"Harry Potters", "en de gevangene van Azkaban", "novel", "EN", "ebook", "uitgeverij de Harmanie",2L );
        Book book = new Book("9789076174105" ,"Harry Potters", "en de gevangene van Azkaban", "novel", "EN", "ebook", "uitgeverij de Harmanie", author1);
        Author author3 = new Author(1L ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        Book book4 = new Book("9789076174105" ,"Harry Potters", "en de gevangene van Azkaban", "novel", "EN", "ebook", "uitgeverij de Harmanie", author3);

        when(bookRepository.findById("9789076174105")).thenReturn(Optional.of(book1));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author2));
        when(bookRepository.save(any())).thenReturn(book4);

        bookService.updateBook(bookInputDto,"9789076174105");

        verify(bookRepository, times(1)).save(captor.capture());

        Book captured = captor.getValue();

        assertEquals(book.getTitle(), captured.getTitle());
        assertEquals(book.getGenre(), captured.getGenre());
        assertEquals(book.getSubtitle(), captured.getSubtitle());
        assertEquals(book.getPublisher(), captured.getPublisher());
        assertEquals(book.getType(), captured.getType());
        assertEquals(book.getLanguage(), captured.getLanguage());
    }

    @Test
    void updateBookThrowsExceptionForBookTest() {
        assertThrows(RecordNotFoundException.class, () -> bookService.updateBook(new BookInputDto("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", 1L), "9789076174103"));
    }

    @Test
    void updateBookThrowsExceptionForAuthorTest() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(book1));
        assertThrows(AuthorNotFoundException.class, () -> bookService.updateBook(new BookInputDto("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", 2L), "9789076174105"));
    }

    @Test
    void deleteBook() {
        bookService.deleteBook("9789076174105");

        verify(bookRepository).deleteById("9789076174105");
    }
}