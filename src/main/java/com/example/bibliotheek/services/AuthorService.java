package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.exceptions.RecordNotFoundException;
import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.repoitories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors(){
        return (transferAuthorListToAuthorDtoList(authorRepository.findAll()));
    }

    public AuthorDto getAuthor(UUID id){

        if(authorRepository.findById(id).isPresent()){
            return (transferAuthorToAuthorDto(authorRepository.findById(id).get()));
        }else {
            throw new RecordNotFoundException("There is no Author known by id" + id);
        }

    }

    public AuthorDto saveAuthor(AuthorDto dto) {
        return (transferAuthorToAuthorDto(authorRepository.save(transferAuthorDtoToAuthor(dto))));
    }

    public AuthorDto updateAuthor(AuthorDto dto, UUID id){
        if(authorRepository.findById(id).isPresent()){
            Author author = authorRepository.findById(id).get();
            if(!author.getInitials().equals(dto.getInitials())){
                author.setInitials(dto.getInitials());
            }
            if(!author.getFirstname().equals(dto.getFirstname())){
                author.setFirstname(dto.getFirstname());
            }
            if(!author.getLastname().equals(dto.getLastname())){
                author.setLastname(dto.getLastname());
            }
            if(!author.getDateOfBirth().equals(dto.getDateOfBirth())){
                author.setDateOfBirth(dto.getDateOfBirth());
            }
            if(!author.getGender().equals(dto.getGender())){
                author.setGender(dto.getGender());
            }

            return (transferAuthorToAuthorDto(authorRepository.save(author)));

        }else {
            throw new RecordNotFoundException("There is no author known by this id " + id);
        }
    }

    public void deleteAuthor(UUID id){
        authorRepository.deleteById(id);
    }

    public Author transferAuthorDtoToAuthor(AuthorDto dto){
        return (new Author(dto.getUuid(), dto.getInitials(), dto.getFirstname(), dto.getLastname(), dto.getDateOfBirth(), dto.getGender()));
    }

    public AuthorDto transferAuthorToAuthorDto(Author author){
        return (new AuthorDto(author.getUuid(), author.getInitials(), author.getFirstname(), author.getLastname(), author.getDateOfBirth(), author.getGender()));
    }

    public List<AuthorDto> transferAuthorListToAuthorDtoList(List<Author> authors){
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (Author author : authors) {
            authorDtos.add(transferAuthorToAuthorDto(author));
        }
        return authorDtos;
    }
}
