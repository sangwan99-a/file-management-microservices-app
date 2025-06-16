package com.example.auth.payload.request;

public class SignupRequest {
    private String email;
    private String password;
    // Add any other fields needed for signup, e.g., username

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
