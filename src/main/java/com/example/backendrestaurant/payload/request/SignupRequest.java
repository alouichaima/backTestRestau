package com.example.backendrestaurant.payload.request;

import java.util.Set;
import jakarta.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    // No-argument constructor
    public SignupRequest() {
    }

    // Parameterized constructor
    public SignupRequest(@NotBlank @Size(min = 3, max = 20) String username,
                         @NotBlank @Size(max = 50) @Email String email, Set<String> role,
                         @NotBlank @Size(min = 6, max = 40) String password) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
