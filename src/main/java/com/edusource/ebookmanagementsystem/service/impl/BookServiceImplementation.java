package com.edusource.ebookmanagementsystem.service.impl;


import com.edusource.ebookmanagementsystem.dto.BookDto;
import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.model.Book;
import com.edusource.ebookmanagementsystem.repository.BookRepository;
import com.edusource.ebookmanagementsystem.service.AWSS3Service;
import com.edusource.ebookmanagementsystem.service.BookService;
import com.edusource.ebookmanagementsystem.util.MappingUtils;
import com.edusource.ebookmanagementsystem.exception.OurException;
//import com.edusource.ebookmanagementsystem.util.AwsS3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AWSS3Service awsS3Utils;

    @Override
    public Response addNewBook(String description, String bookType, String bookTitle, MultipartFile bookPhoto, MultipartFile bookFile) {
        Response response = new Response();

        try {
            // Upload files to AWS S3
            String photoUrl = awsS3Utils.saveBookFileToS3(bookPhoto,"image");
            String fileUrl = awsS3Utils.saveBookFileToS3(bookFile,"pdf");

            // Create and save the new book
            Book book = new Book();
//            book.setId(bookId);
            book.setBookDescription(description);
            book.setBookType(bookType);
            book.setBookTitle(bookTitle);
            book.setBookPhotoUrl(photoUrl);
            book.setBookFileUrl(fileUrl);

            bookRepository.save(book);

            response.setStatusCode(201);
            response.setMessage("Book added successfully.");
            response.setBookDto(MappingUtils.mapBookModelToBookDto(book));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while adding new book: " + e.getMessage());
        }

        return response;
    }

    @Override
    public List<String> getAllBookTypes() {
        return bookRepository.findAll().stream()
                .map(Book::getBookType)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Response getAllBooks() {
        Response response = new Response();

        try {
            List<Book> books = bookRepository.findAll();
            List<BookDto> bookDtos = books.stream()
                    .map(MappingUtils::mapBookModelToBookDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Books retrieved successfully.");
            response.setBookList(bookDtos);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while fetching books: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteBook(Long bookId) {
        Response response = new Response();

        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new OurException("Book not found."));

            // Remove files from AWS S3
            awsS3Utils.deleteFileFromS3(book.getBookPhotoUrl());
            awsS3Utils.deleteFileFromS3(book.getBookFileUrl());

            // Delete book from repository
            bookRepository.delete(book);

            response.setStatusCode(200);
            response.setMessage("Book deleted successfully.");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while deleting book: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateBook(Long bookId, String description, String bookType, String bookTitle, MultipartFile bookPhoto, MultipartFile bookFile) {
        Response response = new Response();

        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new OurException("Book not found."));

            // Update book details
            if (description != null) book.setBookDescription(description);
            if (bookType != null) book.setBookType(bookType);
            if (bookTitle != null) book.setBookTitle(bookTitle);

            if (bookPhoto != null) {
                awsS3Utils.deleteFileFromS3(book.getBookPhotoUrl());
                book.setBookPhotoUrl(awsS3Utils.saveBookFileToS3(bookPhoto,"image"));
            }

            if (bookFile != null) {
                awsS3Utils.deleteFileFromS3(book.getBookFileUrl());
                book.setBookFileUrl(awsS3Utils.saveBookFileToS3(bookFile,"pdf"));
            }

            bookRepository.save(book);

            response.setStatusCode(200);
            response.setMessage("Book updated successfully.");
            response.setBookDto(MappingUtils.mapBookModelToBookDto(book));
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while updating book: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getBookByTitle(String title) {
        Response response = new Response();

        try {
            List<Book> books = bookRepository.findByBookTitleContainingIgnoreCase(title);

            if (books.isEmpty()) {
                throw new OurException("No books found with the given title.");
            }

            List<BookDto> bookDtos = books.stream()
                    .map(MappingUtils::mapBookModelToBookDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Books retrieved successfully.");
            response.setBookList(bookDtos);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while fetching books: " + e.getMessage());
        }

        return response;
    }
}
