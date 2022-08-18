package com.example.bibliotheek.dtos;

import com.example.bibliotheek.models.Author;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class BookDto {

    private String isbn;
    private String title;
    private String subtitle;
    private String genre;
    private String language;
    private String type;
    private String publisher;
    private Author author;

    public BookDto(String isbn, String title, String subtitle, String genre, String language, String type, String publisher, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.genre = genre;
        this.language = language;
        this.type = type;
        this.publisher = publisher;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getType() {
        return type;
    }

    public String getPublisher() {
        return publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
