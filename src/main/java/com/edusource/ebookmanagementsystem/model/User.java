package com.edusource.ebookmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Your email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Your name is required")
    private String name;

    @NotBlank(message = "Your phone no. is required")
    private String phone;

    @NotBlank(message = "Your password is required and please remember it")
    private String password;

    private String role;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReadBook> readBooks=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<ReadBook> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(List<ReadBook> readBooks) {
        this.readBooks = readBooks;
    }

    public @NotBlank(message = "Your email is required") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Your email is required") String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Your name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Your name is required") String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank(message = "Your phone no. is required") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Your phone no. is required") String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
