package com.edusource.ebookmanagementsystem.service.impl;

import com.edusource.ebookmanagementsystem.dto.ReadBookDto;
import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.exception.OurException;
import com.edusource.ebookmanagementsystem.model.Book;
import com.edusource.ebookmanagementsystem.model.ReadBook;
import com.edusource.ebookmanagementsystem.model.User;
import com.edusource.ebookmanagementsystem.repository.BookRepository;
import com.edusource.ebookmanagementsystem.repository.ReadBookRepository;
import com.edusource.ebookmanagementsystem.repository.UserRepository;
import com.edusource.ebookmanagementsystem.service.ReadBookService;
import com.edusource.ebookmanagementsystem.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadBookServiceImplementation implements ReadBookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReadBookRepository readBookRepository;

    @Override
    public Response getUserReadingHistory(String email) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));

            List<ReadBookDto> readingHistory = MappingUtils.mapReadBookEntityToReadBookDTO(user.getReadBooks());
            response.setStatusCode(200);
            response.setMessage("Reading history fetched successfully");
            response.setReadBookList(readingHistory);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching reading history: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response addBookToUserReadingList(String email, Long bookId) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new OurException("Book not found"));

            ReadBook readBook = new ReadBook();
            readBook.setUser(user);
            readBook.setBook(book);
            readBookRepository.save(readBook);

            response.setStatusCode(201);
            response.setMessage("Book added to reading list successfully");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding book to reading list: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response removeBookFromUserReadingList(String email, Long bookId) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));


            ReadBook readBook = readBookRepository.findByUserIdAndBookId(user.getId(),bookId)
                    .orElseThrow(() -> new OurException("Book not found in reading list"));

            readBookRepository.delete(readBook);

            response.setStatusCode(200);
            response.setMessage("Book removed from reading list successfully");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing book from reading list: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getReadingList(User user) {
        Response response = new Response();
        try {
            // Validate the user exists
            User foundUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new OurException("User not found"));

            // Fetch the reading list for the user
            List<ReadBook> readingList = readBookRepository.findByUserId(foundUser.getId());

            // Map the list of ReadBook entities to ReadBookDto for the response
            List<ReadBookDto> readingListDto = MappingUtils.mapReadBookEntityToReadBookDTO(readingList);

            // Build and return the response
            response.setStatusCode(200);
            response.setMessage("Reading list fetched successfully");
            response.setReadBookList(readingListDto);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching reading list: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response addBookToUserReadingHistory(String email, Long bookId) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new OurException("Book not found"));

            ReadBook readBook = new ReadBook();
            readBook.setUser(user);
            readBook.setBook(book);
            readBookRepository.save(readBook);

            response.setStatusCode(201);
            response.setMessage("Book added to reading history successfully");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding book to reading history: " + e.getMessage());
        }
        return response;
    }


}
