package com.example.bibliotheek.dtos;

import java.util.UUID;

public class BookInputDto {

    private String isbn;
    private String title;
    private String subtitle;
    private String genre;
    private String language;
    private String type;
    private String publisher;

    private UUID uuid;


    public BookInputDto(String isbn, String title, String subtitle, String genre, String language, String type, String publisher, UUID uuid) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.genre = genre;
        this.language = language;
        this.type = type;
        this.publisher = publisher;
        this.uuid = uuid;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setIsbn (String isbn){
        this.isbn = isbn;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public void setSubtitle (String subtitle){
        this.subtitle = subtitle;
    }

    public void setGenre (String genre){
        this.genre = genre;
    }

    public void setLanguage (String language){
        this.language = language;
    }

    public void setType (String type){
        this.type = type;
    }

    public void setPublisher (String publisher){
        this.publisher = publisher;
    }

    public void setUuid (UUID uuid){
        this.uuid = uuid;
    }
}



