package com.example.bibliotheek.dtos;


public record BookDto(String isbn, String title, String subtitle, String genre, String language, String type,
                      String publisher, AuthorDto authorDto) {

}
