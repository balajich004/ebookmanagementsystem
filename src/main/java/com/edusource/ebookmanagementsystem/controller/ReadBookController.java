package com.edusource.ebookmanagementsystem.controller;

import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.service.ReadBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/readBooks")
public class ReadBookController {

    @Autowired
    private ReadBookService readBookService;

    // Add a book to the user's reading list
    @PostMapping("/add/{bookId}")
    public ResponseEntity<Response> addBookToReadingList(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = readBookService.addBookToUserReadingList(email, bookId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Get the reading history of the logged-in user
    @GetMapping("/my-history")
    public ResponseEntity<Response> getMyReadingHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = readBookService.getUserReadingHistory(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/reading-list")
    public ResponseEntity<Response> getMyReadingList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = readBookService.getUserReadingHistory(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);

    }

    // Remove a book from the user's reading list
    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<Response> removeBookFromReadingList(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = readBookService.removeBookFromUserReadingList(email, bookId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Add a book to the user's reading history when they view it
    @PostMapping("/history/add/{bookId}")
    public ResponseEntity<Response> addBookToReadingHistory(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = readBookService.addBookToUserReadingHistory(email, bookId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
