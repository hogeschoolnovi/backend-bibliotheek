package com.example.bibliotheek.repoitories;

import com.example.bibliotheek.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByInitialsAndLastnameEqualsIgnoreCase(String initials, String lastname);
}
