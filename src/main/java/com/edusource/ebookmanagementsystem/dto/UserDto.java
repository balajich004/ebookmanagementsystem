package com.edusource.ebookmanagementsystem.dto;

//import com.edusource.ebookmanagementsystem.model.ReadBook;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;

    private String email;

    private String phone;

    private String name;

    private String role;

    private List<ReadBookDto> readBooks=new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ReadBookDto> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(List<ReadBookDto> readBooks) {
        this.readBooks = readBooks;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
