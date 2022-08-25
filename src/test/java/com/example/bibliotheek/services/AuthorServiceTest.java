package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.exceptions.RecordNotFoundException;
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
        author1 = new Author(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        author2 = new Author(UUID.fromString("70080f94-539c-466d-9976-b838dc037842"),"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);

    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> dtos = authorService.getAllAuthors();

        assertEquals(authors.get(0).getFirstname(), dtos.get(0).getFirstname());
        assertEquals(authors.get(0).getGender(), dtos.get(0).getGender());
        assertEquals(authors.get(0).getLastname(), dtos.get(0).getLastname());
        assertEquals(authors.get(0).getInitials(), dtos.get(0).getInitials());
        assertEquals(authors.get(0).getDateOfBirth(), dtos.get(0).getDateOfBirth());
        assertEquals(authors.get(0).getUuid(), dtos.get(0).getUuid());

    }

    @Test
    void getAuthor() {
       UUID uuid = UUID.fromString("70080f94-539c-466d-9976-b838dc037842");

        when(authorRepository.findById(uuid)).thenReturn(Optional.of(author2));

        Author author = authorRepository.findById(uuid).get();
        AuthorDto dto = authorService.getAuthor(uuid);

        assertEquals(author.getFirstname(), dto.getFirstname());
        assertEquals(author.getGender(), dto.getGender());
        assertEquals(author.getLastname(), dto.getLastname());
        assertEquals(author.getInitials(), dto.getInitials());
        assertEquals(author.getDateOfBirth(), dto.getDateOfBirth());

    }

    @Test
    void getAuthorThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> authorService.getAuthor(null));
    }


    @Test
    void saveAuthor() {
        when(authorRepository.save(author1)).thenReturn(author1);
        authorService.saveAuthor(authorService.transferAuthorToAuthorDto(author1));
        verify(authorRepository, times(1)).save(argumentCaptor.capture());
        Author author = argumentCaptor.getValue();

        assertEquals(author1.getUuid(), author.getUuid());
        assertEquals(author1.getFirstname(), author.getFirstname());
        assertEquals(author1.getGender(), author.getGender());
        assertEquals(author1.getLastname(), author.getLastname());
        assertEquals(author1.getInitials(), author.getInitials());
        assertEquals(author1.getDateOfBirth(), author.getDateOfBirth());
    }


    @Test
    void updateAuthor() {
        when(authorRepository.findById(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"))).thenReturn(Optional.of(author1));

        AuthorDto authorDto = new AuthorDto(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K", "Joanne Katharina", "Unknown", LocalDate.of(1968,7,31), Gender.OTHER);

        when(authorRepository.save(authorService.transferAuthorDtoToAuthor(authorDto))).thenReturn(author1);

        authorService.updateAuthor(authorDto, UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"));

        verify(authorRepository, times(1)).save(argumentCaptor.capture());

        Author captured = argumentCaptor.getValue();

        assertEquals(authorDto.getUuid(), captured.getUuid());
        assertEquals(authorDto.getFirstname(), captured.getFirstname());
        assertEquals(authorDto.getGender(), captured.getGender());
        assertEquals(authorDto.getLastname(), captured.getLastname());
        assertEquals(authorDto.getInitials(), captured.getInitials());
        assertEquals(authorDto.getDateOfBirth(), captured.getDateOfBirth());
    }

    @Test
    void updateAuthorThrowsExceptionTest() {
        AuthorDto authorDto = new AuthorDto(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K", "Joanne Katharina", "Unknown", LocalDate.of(1968,7,31), Gender.OTHER);

        assertThrows(RecordNotFoundException.class, () -> authorService.updateAuthor(authorDto, null));
    }

    @Test
    void deleteAuthor() {
        authorService.deleteAuthor(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"));

        verify(authorRepository).deleteById(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"));

    }


}