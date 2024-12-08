package com.edusource.ebookmanagementsystem.service;

import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.model.User;

public interface ReadBookService {
    Response getUserReadingHistory(String email);

    Response addBookToUserReadingList(String email, Long bookId);

    Response removeBookFromUserReadingList(String email, Long bookId);

    Response getReadingList(User user);

    Response addBookToUserReadingHistory(String email, Long bookId);
}
