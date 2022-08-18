package com.example.bibliotheek.repoitories;

import com.example.bibliotheek.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByInitialsAndLastnameEqualsIgnoreCase(String name);
}
