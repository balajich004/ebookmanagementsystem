package com.edusource.ebookmanagementsystem.repository;

import com.edusource.ebookmanagementsystem.model.ReadBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadBookRepository extends JpaRepository<ReadBook,Long> {

    Optional<ReadBook> findByUserIdAndBookId(Long userId, Long bookId);

    List<ReadBook> findByUserId(Long userId);

}
