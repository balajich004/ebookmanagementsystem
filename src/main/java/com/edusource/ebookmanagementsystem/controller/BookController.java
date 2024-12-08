package com.edusource.ebookmanagementsystem.controller;

import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.service.BookService;
import com.edusource.ebookmanagementsystem.service.ReadBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReadBookService readBookService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewBook(
            @RequestParam(value = "photo", required = false) MultipartFile bookPhoto,
            @RequestParam(value = "pdf", required = false) MultipartFile bookFile,
            @RequestParam(value = "bookType", required = false) String bookType,
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            @RequestParam(value = "bookDescription", required = false) String bookDescription
    ) {

        if (bookFile.isEmpty() || bookPhoto.isEmpty() || bookTitle == null || bookType.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(photo, bookType,bookTitle)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = bookService.addNewBook(bookDescription,bookType,bookTitle,bookPhoto,bookFile);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBooks() {
        Response response = bookService.getAllBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getBookTypes() {
        return bookService.getAllBookTypes();
    }

    @GetMapping("/book-by-id")
    public ResponseEntity<Response> getBookById(@RequestParam String bookTitle) {
        Response response = bookService.getBookByTitle(bookTitle);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/update/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long bookId,
                                               @RequestParam(value = "photo", required = false) MultipartFile bookPhoto,
                                               @RequestParam(value = "pdf", required = false) MultipartFile bookFile,
                                               @RequestParam(value = "bookType", required = false) String bookType,
                                               @RequestParam(value = "bookTitle", required = false) String bookTitle,
                                               @RequestParam(value = "bookDescription", required = false) String bookDescription

    ) {
        Response response = bookService.updateBook(bookId, bookDescription, bookType, bookTitle, bookPhoto,bookFile);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long bookId) {
        Response response = bookService.deleteBook(bookId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

}
