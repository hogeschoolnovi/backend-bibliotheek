package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.exceptions.AuthorNotFoundException;
import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Gender;
import com.example.bibliotheek.repoitories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorService authorService;

    @Captor
    ArgumentCaptor<Author> argumentCaptor;

    Author author1;
    Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author(1L ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        author2 = new Author(2L,"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);

    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> dtos = authorService.getAllAuthors();

        assertEquals(authors.get(0).getFirstname(), dtos.get(0).firstname());
        assertEquals(authors.get(0).getGender(), dtos.get(0).gender());
        assertEquals(authors.get(0).getLastname(), dtos.get(0).lastname());
        assertEquals(authors.get(0).getInitials(), dtos.get(0).initials());
        assertEquals(authors.get(0).getDateOfBirth(), dtos.get(0).dateOfBirth());
        assertEquals(authors.get(0).getId(), dtos.get(0).id());

    }

    @Test
    void getAuthor() {
       Long id = 2L;

        when(authorRepository.findById(id)).thenReturn(Optional.of(author2));

        Author author = authorRepository.findById(id).get();
        AuthorDto dto = authorService.getAuthor(id);

        assertEquals(author.getFirstname(), dto.firstname());
        assertEquals(author.getGender(), dto.gender());
        assertEquals(author.getLastname(), dto.lastname());
        assertEquals(author.getInitials(), dto.initials());
        assertEquals(author.getDateOfBirth(), dto.dateOfBirth());

    }

    @Test
    void getAuthorThrowsExceptionTest() {
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthor(null));
    }


    @Test
    void saveAuthor() {
        when(authorRepository.save(author1)).thenReturn(author1);
        authorService.saveAuthor(authorService.transferAuthorToAuthorDto(author1));
        verify(authorRepository, times(1)).save(argumentCaptor.capture());
        Author author = argumentCaptor.getValue();

        assertEquals(author1.getId(), author.getId());
        assertEquals(author1.getFirstname(), author.getFirstname());
        assertEquals(author1.getGender(), author.getGender());
        assertEquals(author1.getLastname(), author.getLastname());
        assertEquals(author1.getInitials(), author.getInitials());
        assertEquals(author1.getDateOfBirth(), author.getDateOfBirth());
    }


    @Test
    void updateAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        AuthorDto authorDto = new AuthorDto(1L ,"J.K", "Joanne Katharina", "Unknown", LocalDate.of(1968,7,31), Gender.OTHER);

        when(authorRepository.save(authorService.transferAuthorDtoToAuthor(authorDto))).thenReturn(author1);

        authorService.updateAuthor(authorDto, 1L);

        verify(authorRepository, times(1)).save(argumentCaptor.capture());

        Author captured = argumentCaptor.getValue();

        assertEquals(authorDto.id(), captured.getId());
        assertEquals(authorDto.firstname(), captured.getFirstname());
        assertEquals(authorDto.gender(), captured.getGender());
        assertEquals(authorDto.lastname(), captured.getLastname());
        assertEquals(authorDto.initials(), captured.getInitials());
        assertEquals(authorDto.dateOfBirth(), captured.getDateOfBirth());
    }

    @Test
    void updateAuthorThrowsExceptionTest() {
        AuthorDto authorDto = new AuthorDto(1L ,"J.K", "Joanne Katharina", "Unknown", LocalDate.of(1968,7,31), Gender.OTHER);

        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(authorDto, null));
    }

    @Test
    void deleteAuthor() {
        authorService.deleteAuthor(1L);

        verify(authorRepository).deleteById(1L);

    }


}