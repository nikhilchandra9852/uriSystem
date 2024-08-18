package com.service.Tiny.Url.Service.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class SignUpEntity {

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

    public SignUpEntity() {
    }

    public SignUpEntity(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public void setRole(@NotBlank String role) {
        this.role = role;
    }
}
