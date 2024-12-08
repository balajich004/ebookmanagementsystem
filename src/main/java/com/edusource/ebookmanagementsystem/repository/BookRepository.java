package com.edusource.ebookmanagementsystem.repository;

import com.edusource.ebookmanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT DISTINCT b.bookType FROM Book b")
    List<String> findDistinctBookTypes();

    List<Book> findByBookTitleContainingIgnoreCase(String title);
}
