package com.example.bibliotheek.services;

import com.example.bibliotheek.dtos.AuthorDto;
import com.example.bibliotheek.exceptions.AuthorNotFoundException;
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

    public AuthorDto getAuthor(Long id){

        if(authorRepository.findById(id).isPresent()){
            return (transferAuthorToAuthorDto(authorRepository.findById(id).get()));
        }else {
            throw new AuthorNotFoundException("There is no Author known by id" + id);
        }

    }

    public AuthorDto saveAuthor(AuthorDto dto) {
        return (transferAuthorToAuthorDto(authorRepository.save(transferAuthorDtoToAuthor(dto))));
    }

    public AuthorDto updateAuthor(AuthorDto dto, Long id){
        if(authorRepository.findById(id).isPresent()){
            Author author = authorRepository.findById(id).get();
            if(!author.getInitials().equals(dto.initials())){
                author.setInitials(dto.initials());
            }
            if(!author.getFirstname().equals(dto.firstname())){
                author.setFirstname(dto.firstname());
            }
            if(!author.getLastname().equals(dto.lastname())){
                author.setLastname(dto.lastname());
            }
            if(!author.getDateOfBirth().equals(dto.dateOfBirth())){
                author.setDateOfBirth(dto.dateOfBirth());
            }
            if(!author.getGender().equals(dto.gender())){
                author.setGender(dto.gender());
            }

            return (transferAuthorToAuthorDto(authorRepository.save(author)));

        }else {
            throw new AuthorNotFoundException("There is no author known by this id " + id);
        }
    }

    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

    public Author transferAuthorDtoToAuthor(AuthorDto dto){
        return (new Author(dto.id(), dto.initials(), dto.firstname(), dto.lastname(), dto.dateOfBirth(), dto.gender()));
    }

    public AuthorDto transferAuthorToAuthorDto(Author author){
        return (new AuthorDto(author.getId(), author.getInitials(), author.getFirstname(), author.getLastname(), author.getDateOfBirth(), author.getGender()));
    }

    public List<AuthorDto> transferAuthorListToAuthorDtoList(List<Author> authors){
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (Author author : authors) {
            authorDtos.add(transferAuthorToAuthorDto(author));
        }
        return authorDtos;
    }
}
