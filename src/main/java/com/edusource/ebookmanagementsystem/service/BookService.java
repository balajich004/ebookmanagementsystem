package com.edusource.ebookmanagementsystem.service;

import com.edusource.ebookmanagementsystem.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    Response addNewBook(String description, String bookType, String bookTitle, MultipartFile bookPhoto,MultipartFile bookFile);

    List<String> getAllBookTypes();

    Response getAllBooks();

    Response deleteBook(Long bookId);

    Response updateBook(Long bookId, String description, String bookType, String bookTitle, MultipartFile bookPhoto,MultipartFile bookFile);

    Response getBookByTitle(String title);


}
