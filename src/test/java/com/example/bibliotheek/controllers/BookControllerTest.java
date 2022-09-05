package com.example.bibliotheek.controllers;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.dtos.BookDto;
import com.example.bibliotheek.dtos.BookInputDto;
import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Book;
import com.example.bibliotheek.models.Gender;
import com.example.bibliotheek.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    Book book1;
    Book book2;
    Book book3;
    BookDto bookDto1;
    BookDto bookDto2;
    BookDto bookDto3;

    BookDto bookDto4;
    Author author1;
    Author author2;
    AuthorDto authorDto1;
    AuthorDto authorDto2;

    BookInputDto bookInputDto1;
    BookInputDto bookInputDto2;

    @BeforeEach
    public void setUp() {
        author1 = new Author(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        author2 = new Author(UUID.fromString("70080f94-539c-466d-9976-b838dc037842"),"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);

        book1 = new Book("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", author1);
        book2 = new Book("9781781103470", "Harry Potter", "en de geheime kamer", "fiction", "NL", "paperback", "Pottermore publishing", author1);
        book3 = new Book("9789026139406", "Mathilda", "", "fiction", "NL", "paperback", "de Fontein Jeugd", author2);

        authorDto1 = new AuthorDto(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        authorDto2 = new AuthorDto(UUID.fromString("70080f94-539c-466d-9976-b838dc037842"),"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);

        bookDto1 = new BookDto("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", authorDto1);
        bookDto2 = new BookDto("9781781103470", "Harry Potter", "en de geheime kamer", "fiction", "NL", "paperback", "Pottermore publishing", authorDto1);
        bookDto3 = new BookDto("9789026139406", "Mathilda", "", "fiction", "NL", "paperback", "de Fontein Jeugd", authorDto2);
        bookDto4 = new BookDto("9789076174105", "Harry Potter", "en de geheime kamer", "sience fiction", "EN", "ebook", "Pottermore publishing", authorDto2);

        bookInputDto1 = new BookInputDto("9789076174105", "Harry Potter", "en de steen der wijzen", "fiction", "NL", "paperback", "uitgeverij de Harmonie", UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"));
        bookInputDto2 = new BookInputDto("9789076174105", "Harry Potter", "en de geheime kamer", "sience fiction", "EN", "ebook", "Pottermore publishing", UUID.fromString("70080f94-539c-466d-9976-b838dc037842"));

    }

    @Test
    void getAllBooks() throws Exception {
        given(bookService.getAllBooks()).willReturn(List.of(bookDto1, bookDto2, bookDto3));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("9789076174105"))
                .andExpect(jsonPath("$[0].title").value("Harry Potter"))
                .andExpect(jsonPath("$[0].subtitle").value("en de steen der wijzen"))
                .andExpect(jsonPath("$[0].genre").value("fiction"))
                .andExpect(jsonPath("$[0].language").value("NL"))
                .andExpect(jsonPath("$[0].type").value("paperback"))
                .andExpect(jsonPath("$[0].publisher").value("uitgeverij de Harmonie"))
                .andExpect(jsonPath("$[0].authorDto.uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                .andExpect(jsonPath("$[0].authorDto.initials").value("J.K."))
                .andExpect(jsonPath("$[0].authorDto.firstname").value( "Joanne Kathleen"))
                .andExpect(jsonPath("$[0].authorDto.lastname").value( "Rowling"))
                .andExpect(jsonPath("$[0].authorDto.dateOfBirth").value("1965-07-31"))
                .andExpect(jsonPath("$[0].authorDto.gender").value( "FEMALE"))
                .andExpect(jsonPath("$[1].isbn").value("9781781103470"))
                .andExpect(jsonPath("$[1].title").value("Harry Potter"))
                .andExpect(jsonPath("$[1].subtitle").value("en de geheime kamer"))
                .andExpect(jsonPath("$[1].genre").value("fiction"))
                .andExpect(jsonPath("$[1].language").value("NL"))
                .andExpect(jsonPath("$[1].type").value("paperback"))
                .andExpect(jsonPath("$[1].publisher").value( "Pottermore publishing"))
                .andExpect(jsonPath("$[1].authorDto.uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                .andExpect(jsonPath("$[1].authorDto.initials").value("J.K."))
                .andExpect(jsonPath("$[1].authorDto.firstname").value( "Joanne Kathleen"))
                .andExpect(jsonPath("$[1].authorDto.lastname").value( "Rowling"))
                .andExpect(jsonPath("$[1].authorDto.dateOfBirth").value("1965-07-31"))
                .andExpect(jsonPath("$[1].authorDto.gender").value( "FEMALE"))
                .andExpect(jsonPath("$[2].isbn").value("9789026139406"))
                .andExpect(jsonPath("$[2].title").value("Mathilda"))
                .andExpect(jsonPath("$[2].subtitle").value( ""))
                .andExpect(jsonPath("$[2].genre").value("fiction"))
                .andExpect(jsonPath("$[2].language").value( "NL"))
                .andExpect(jsonPath("$[2].type").value("paperback"))
                .andExpect(jsonPath("$[2].publisher").value("de Fontein Jeugd"))
                .andExpect(jsonPath("$[2].authorDto.uuid").value("70080f94-539c-466d-9976-b838dc037842"))
                .andExpect(jsonPath("$[2].authorDto.initials").value("R."))
                .andExpect(jsonPath("$[2].authorDto.firstname").value( "Roald"))
                .andExpect(jsonPath("$[2].authorDto.lastname").value( "Dahl"))
                .andExpect(jsonPath("$[2].authorDto.dateOfBirth").value("1916-09-13"))
                .andExpect(jsonPath("$[2].authorDto.gender").value( "MALE"));
    }

    @Test
    void getAllBooksByAuthor() throws Exception {
        given(bookService.getAllBooksByAuthor("J.K.", "Rowling")).willReturn(List.of(bookDto1, bookDto2));

        mockMvc.perform(get("/books?initials=J.K.&&name=Rowling"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("9789076174105"))
                .andExpect(jsonPath("$[0].title").value("Harry Potter"))
                .andExpect(jsonPath("$[0].subtitle").value("en de steen der wijzen"))
                .andExpect(jsonPath("$[0].genre").value("fiction"))
                .andExpect(jsonPath("$[0].language").value("NL"))
                .andExpect(jsonPath("$[0].type").value("paperback"))
                .andExpect(jsonPath("$[0].publisher").value("uitgeverij de Harmonie"))
                .andExpect(jsonPath("$[0].authorDto.uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                .andExpect(jsonPath("$[0].authorDto.initials").value("J.K."))
                .andExpect(jsonPath("$[0].authorDto.firstname").value( "Joanne Kathleen"))
                .andExpect(jsonPath("$[0].authorDto.lastname").value( "Rowling"))
                .andExpect(jsonPath("$[0].authorDto.dateOfBirth").value("1965-07-31"))
                .andExpect(jsonPath("$[0].authorDto.gender").value( "FEMALE"))
                .andExpect(jsonPath("$[1].isbn").value("9781781103470"))
                .andExpect(jsonPath("$[1].title").value("Harry Potter"))
                .andExpect(jsonPath("$[1].subtitle").value("en de geheime kamer"))
                .andExpect(jsonPath("$[1].genre").value("fiction"))
                .andExpect(jsonPath("$[1].language").value("NL"))
                .andExpect(jsonPath("$[1].type").value("paperback"))
                .andExpect(jsonPath("$[1].publisher").value( "Pottermore publishing"))
                .andExpect(jsonPath("$[1].authorDto.uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                .andExpect(jsonPath("$[1].authorDto.initials").value("J.K."))
                .andExpect(jsonPath("$[1].authorDto.firstname").value( "Joanne Kathleen"))
                .andExpect(jsonPath("$[1].authorDto.lastname").value( "Rowling"))
                .andExpect(jsonPath("$[1].authorDto.dateOfBirth").value("1965-07-31"))
                .andExpect(jsonPath("$[1].authorDto.gender").value( "FEMALE"));

    }

    @Test
    void getAllBooksByTitle() throws Exception {
        given(bookService.getAllBooksByTitle("Mathilda")).willReturn(List.of(bookDto3));

        mockMvc.perform(get("/books?title=Mathilda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("9789026139406"))
                .andExpect(jsonPath("$[0].title").value("Mathilda"))
                .andExpect(jsonPath("$[0].subtitle").value( ""))
                .andExpect(jsonPath("$[0].genre").value("fiction"))
                .andExpect(jsonPath("$[0].language").value( "NL"))
                .andExpect(jsonPath("$[0].type").value("paperback"))
                .andExpect(jsonPath("$[0].publisher").value("de Fontein Jeugd"))
                .andExpect(jsonPath("$[0].authorDto.uuid").value("70080f94-539c-466d-9976-b838dc037842"))
                .andExpect(jsonPath("$[0].authorDto.initials").value("R."))
                .andExpect(jsonPath("$[0].authorDto.firstname").value( "Roald"))
                .andExpect(jsonPath("$[0].authorDto.lastname").value( "Dahl"))
                .andExpect(jsonPath("$[0].authorDto.dateOfBirth").value("1916-09-13"))
                .andExpect(jsonPath("$[0].authorDto.gender").value( "MALE"));
    }


    @Test
    void getBook() throws Exception {
        given(bookService.getBook(anyString())).willReturn(bookDto1);

        mockMvc.perform(get("/books/9789076174105"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("isbn").value("9789076174105"))
                .andExpect(jsonPath("title").value("Harry Potter"))
                .andExpect(jsonPath("subtitle").value("en de steen der wijzen"))
                .andExpect(jsonPath("genre").value("fiction"))
                .andExpect(jsonPath("language").value("NL"))
                .andExpect(jsonPath("type").value("paperback"))
                .andExpect(jsonPath("publisher").value("uitgeverij de Harmonie"))
                .andExpect(jsonPath("authorDto.uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                .andExpect(jsonPath("authorDto.initials").value("J.K."))
                .andExpect(jsonPath("authorDto.firstname").value( "Joanne Kathleen"))
                .andExpect(jsonPath("authorDto.lastname").value( "Rowling"))
                .andExpect(jsonPath("authorDto.dateOfBirth").value("1965-07-31"))
                .andExpect(jsonPath("authorDto.gender").value( "FEMALE"));
    }

    @Test
    void saveBook() throws Exception {
        given(bookService.saveBook(bookInputDto1)).willReturn(bookDto1);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookInputDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("isbn").value("9789076174105"))
                .andExpect(jsonPath("title").value("Harry Potter"))
                .andExpect(jsonPath("subtitle").value("en de steen der wijzen"))
                .andExpect(jsonPath("genre").value("fiction"))
                .andExpect(jsonPath("language").value("NL"))
                .andExpect(jsonPath("type").value("paperback"))
                .andExpect(jsonPath("publisher").value("uitgeverij de Harmonie"))
                .andExpect(jsonPath("authorDto.uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                .andExpect(jsonPath("authorDto.initials").value("J.K."))
                .andExpect(jsonPath("authorDto.firstname").value( "Joanne Kathleen"))
                .andExpect(jsonPath("authorDto.lastname").value( "Rowling"))
                .andExpect(jsonPath("authorDto.dateOfBirth").value("1965-07-31"))
                .andExpect(jsonPath("authorDto.gender").value( "FEMALE"));
    }



    @Test
    void updateBook() throws Exception {
        given(bookService.updateBook(bookInputDto2, "9789076174105")).willReturn(bookDto4);

        mockMvc.perform(put("/books/update/9789076174105")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookInputDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("isbn").value("9789076174105"))
                .andExpect(jsonPath("title").value("Harry Potter"))
                .andExpect(jsonPath("subtitle").value("en de geheime kamer"))
                .andExpect(jsonPath("genre").value("sience fiction"))
                .andExpect(jsonPath("language").value("EN"))
                .andExpect(jsonPath("type").value("ebook"))
                .andExpect(jsonPath("publisher").value("Pottermore publishing"))
                .andExpect(jsonPath("authorDto.uuid").value("70080f94-539c-466d-9976-b838dc037842"))
                .andExpect(jsonPath("authorDto.initials").value("R."))
                .andExpect(jsonPath("authorDto.firstname").value( "Roald"))
                .andExpect(jsonPath("authorDto.lastname").value( "Dahl"))
                .andExpect(jsonPath("authorDto.dateOfBirth").value("1916-09-13"))
                .andExpect(jsonPath("authorDto.gender").value( "MALE"));
    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/books/delete/9789076174105"))
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}