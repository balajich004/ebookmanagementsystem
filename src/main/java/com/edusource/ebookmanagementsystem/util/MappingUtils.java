package com.edusource.ebookmanagementsystem.util;

import com.edusource.ebookmanagementsystem.dto.BookDto;
import com.edusource.ebookmanagementsystem.dto.ReadBookDto;
import com.edusource.ebookmanagementsystem.dto.UserDto;
import com.edusource.ebookmanagementsystem.model.Book;
import com.edusource.ebookmanagementsystem.model.ReadBook;
import com.edusource.ebookmanagementsystem.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    public static UserDto mapUserReadingHistoryToUserDto(User user) {
        UserDto userDto = mapUserModelToUserDto(user); // Map basic user details

        // Map reading history (readBooks)
        if (user.getReadBooks() != null && !user.getReadBooks().isEmpty()) {
            userDto.setReadBooks(user.getReadBooks().stream()
                    .map(MappingUtils::mapReadBookModelToReadBookDto)
                    .collect(Collectors.toList()));
        }

        return userDto;
    }



    // Method to map a list of User entities to a list of UserDTOs
    public static List<UserDto> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream()
                .map(MappingUtils::mapUserModelToUserDto)
                .collect(Collectors.toList());
    }

    // Method to map a list of Book entities to a list of BookDTOs
    public static List<BookDto> mapBookListEntityToBookListDTO(List<Book> bookList) {
        return bookList.stream()
                .map(MappingUtils::mapBookModelToBookDto)
                .collect(Collectors.toList());
    }

    // Method to map a list of ReadBook entities to a list of ReadBookDTOs
    public static List<ReadBookDto> mapReadBookEntityToReadBookDTO(List<ReadBook> readBooks) {
        return readBooks.stream()
                .map(MappingUtils::mapReadBookModelToReadBookDto)
                .collect(Collectors.toList());
    }

    // Method to map a single User entity to UserDTO
    public static UserDto mapUserModelToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());

        // Safely map the reading list (readBooks) for the user
        if (user.getReadBooks() != null) {
            if (!user.getReadBooks().isEmpty()) {
                userDto.setReadBooks(user.getReadBooks().stream()
                        .map(MappingUtils::mapReadBookModelToReadBookDto)
                        .collect(Collectors.toList()));
            } else {
                userDto.setReadBooks(Collections.emptyList()); // Ensure it's never null
            }
        } else {
            userDto.setReadBooks(Collections.emptyList()); // Fallback if readBooks is null
        }

        return userDto;
    }


    // Method to map a single Book entity to BookDTO
    public static BookDto mapBookModelToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setBookTitle(book.getBookTitle());
        bookDto.setBookDescription(book.getBookDescription());
        bookDto.setBookPhotoUrl(book.getBookPhotoUrl());
        bookDto.setBookFileUrl(book.getBookFileUrl());
        bookDto.setBookType(book.getBookType());
        return bookDto;
    }

    // Method to map a single ReadBook entity to ReadBookDTO
//    public static ReadBookDto mapReadBookModelToReadBookDto(ReadBook readBook) {
//        ReadBookDto readBookDto = new ReadBookDto();
//        readBookDto.setId(readBook.getId());
//        readBookDto.setBookDto(mapBookModelToBookDto(readBook.getBook()));
//        readBookDto.setUserDto(mapUserModelToUserDto(readBook.getUser()));
//        return readBookDto;
//    }

    public static ReadBookDto mapReadBookModelToReadBookDto(ReadBook readBook) {
        ReadBookDto readBookDto = new ReadBookDto();
        readBookDto.setId(readBook.getId());
        readBookDto.setBookDto(mapBookModelToBookDto(readBook.getBook()));

        // Simplify user mapping to avoid recursion
        UserDto userDto = new UserDto();
        userDto.setId(readBook.getUser().getId());
        userDto.setName(readBook.getUser().getName());
        readBookDto.setUserDto(userDto);

        return readBookDto;
    }
}
