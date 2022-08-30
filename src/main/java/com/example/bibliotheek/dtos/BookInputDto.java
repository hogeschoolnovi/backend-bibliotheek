package com.example.bibliotheek.dtos;

import java.util.UUID;

public record BookInputDto(String isbn, String title, String subtitle, String genre, String language, String type,
                           String publisher, UUID uuid) {

}



