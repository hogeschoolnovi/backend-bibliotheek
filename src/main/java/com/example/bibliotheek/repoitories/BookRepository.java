package com.example.bibliotheek.repoitories;

import com.example.bibliotheek.models.Author;
import com.example.bibliotheek.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAllByTitle(String title);
    List<Book> findAllByAuthor(Author author);

}
