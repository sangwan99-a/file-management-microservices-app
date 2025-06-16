package com.example.auth.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id; // Optional: user ID
    private String email; // Optional: user email

    public JwtResponse(String accessToken, Long id, String email) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
    }

    public JwtResponse(String accessToken, String email) {
        this.token = accessToken;
        this.email = email;
    }


    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
