package com.edusource.ebookmanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookType;

    private String bookTitle;

    private String bookPhotoUrl;

    private String bookFileUrl;

    private String bookDescription;

    public String getBookPhotoUrl() {
        return bookPhotoUrl;
    }

    public void setBookPhotoUrl(String bookPhotoUrl) {
        this.bookPhotoUrl = bookPhotoUrl;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String description) {
        this.bookDescription = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookFileUrl() {
        return bookFileUrl;
    }

    public void setBookFileUrl(String bookFileUrl) {
        this.bookFileUrl = bookFileUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookDescription='" + bookDescription + '\'' +
                ", id=" + id +
                ", bookType='" + bookType + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookPhotoUrl='" + bookPhotoUrl + '\'' +
                ", bookFileUrl='" + bookFileUrl + '\'' +
                '}';
    }
}
