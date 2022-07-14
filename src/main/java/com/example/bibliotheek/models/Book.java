package com.example.bibliotheek.models;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    private String isbn;

    private String title;
    private String subtitle;
    private String genre;
    private String language;
    private String type;
    private String publisher;

    @ManyToOne
    private Author author;

    public Book(String isbn, String title, String subtitle, String genre, String language, String type, String publisher, Author author) {
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
}
