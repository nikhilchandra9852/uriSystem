package com.service.Tiny.Url.Service.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class SignUp {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank
    @Size(min=3,max = 30)
    private String username;

    @NotBlank
    @Size(min=8,max=30)
    private String password;

    @Email
    @NotBlank
    @Size(max=50)
    private String email;

    @NotBlank
    private String role;

    public SignUp() {
    }

    public SignUp(String username, String password, String email) {
        this.id= UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public @NotBlank @Size(min = 3, max = 30) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 30) String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 8, max = 30) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, max = 30) String password) {
        this.password = password;
    }

    public @Email @NotBlank @Size(max = 50) String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank @Size(max = 50) String email) {
        this.email = email;
    }

    public @NotBlank String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

