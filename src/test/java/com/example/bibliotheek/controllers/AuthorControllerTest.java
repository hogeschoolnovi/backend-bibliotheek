package com.example.bibliotheek.controllers;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Gender;
import com.example.bibliotheek.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    Author author1;
    Author author2;
    AuthorDto authorDto1;
    AuthorDto authorDto2;

    AuthorDto authorDto3;

    @BeforeEach
    public void setUp() {
        author1 = new Author(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        author2 = new Author(UUID.fromString("70080f94-539c-466d-9976-b838dc037842"),"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);

        authorDto1 = new AuthorDto(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336") ,"J.K.", "Joanne Kathleen", "Rowling", LocalDate.of(1965,7,31), Gender.FEMALE);
        authorDto2 = new AuthorDto(UUID.fromString("70080f94-539c-466d-9976-b838dc037842"),"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);
        authorDto3 = new AuthorDto(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"),"R.", "Roald", "Dahl", LocalDate.of(1916,9,13), Gender.MALE);


    }

    @Test
    void getAllAuthors() throws Exception {
            given(authorService.getAllAuthors()).willReturn(List.of(authorDto1, authorDto2));

            mockMvc.perform(get("/authors"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                    .andExpect(jsonPath("$[0].initials").value("J.K."))
                    .andExpect(jsonPath("$[0].firstname").value( "Joanne Kathleen"))
                    .andExpect(jsonPath("$[0].lastname").value( "Rowling"))
                    .andExpect(jsonPath("$[0].dateOfBirth").value("1965-07-31"))
                    .andExpect(jsonPath("$[0].gender").value( "FEMALE"))
                    .andExpect(jsonPath("$[1].uuid").value("70080f94-539c-466d-9976-b838dc037842"))
                    .andExpect(jsonPath("$[1].initials").value("R."))
                    .andExpect(jsonPath("$[1].firstname").value( "Roald"))
                    .andExpect(jsonPath("$[1].lastname").value( "Dahl"))
                    .andExpect(jsonPath("$[1].dateOfBirth").value("1916-09-13"))
                    .andExpect(jsonPath("$[1].gender").value( "MALE"));
        }

    @Test
    void getAuthor() throws Exception {
            given(authorService.getAuthor(UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"))).willReturn(authorDto1);

            mockMvc.perform(get("/authors/aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                    .andExpect(jsonPath("initials").value("J.K."))
                    .andExpect(jsonPath("firstname").value( "Joanne Kathleen"))
                    .andExpect(jsonPath("lastname").value( "Rowling"))
                    .andExpect(jsonPath("dateOfBirth").value("1965-07-31"))
                    .andExpect(jsonPath("gender").value( "FEMALE"));
        }

    @Test
    void saveAuthor()  throws Exception {
            given(authorService.saveAuthor(authorDto1)).willReturn(authorDto1);

            mockMvc.perform(post("/authors")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(authorDto1)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                    .andExpect(jsonPath("initials").value("J.K."))
                    .andExpect(jsonPath("firstname").value( "Joanne Kathleen"))
                    .andExpect(jsonPath("lastname").value( "Rowling"))
                    .andExpect(jsonPath("dateOfBirth").value("1965-07-31"))
                    .andExpect(jsonPath("gender").value( "FEMALE"));
        }

    @Test
    void updateAuthor() throws Exception {
            given(authorService.updateAuthor(authorDto2, UUID.fromString("aabe4998-522a-4bd7-97c4-c296b7fb0336"))).willReturn(authorDto3);

            mockMvc.perform(put("/authors/update/aabe4998-522a-4bd7-97c4-c296b7fb0336")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(authorDto2)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("uuid").value("aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                    .andExpect(jsonPath("initials").value("R."))
                    .andExpect(jsonPath("firstname").value( "Roald"))
                    .andExpect(jsonPath("lastname").value( "Dahl"))
                    .andExpect(jsonPath("dateOfBirth").value("1916-09-13"))
                    .andExpect(jsonPath("gender").value( "MALE"));
        }

    @Test
    void deleteAuthor() throws Exception {
            mockMvc.perform(delete("/authors/delete/aabe4998-522a-4bd7-97c4-c296b7fb0336"))
                    .andExpect(status().isOk());
        }
        public static String asJsonString(final AuthorDto obj) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                return mapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

}