package com.example.bibliotheek.models;

import javax.persistence.*;
import java.util.Objects;

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

    public Book() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getIsbn().equals(book.getIsbn()) && getTitle().equals(book.getTitle()) && getSubtitle().equals(book.getSubtitle()) && getGenre().equals(book.getGenre()) && getLanguage().equals(book.getLanguage()) && getType().equals(book.getType()) && getPublisher().equals(book.getPublisher()) && getAuthor().equals(book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getSubtitle(), getGenre(), getLanguage(), getType(), getPublisher(), getAuthor());
    }
}
