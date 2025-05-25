package com.example.vaultauth.model;

// model/LoginRequest.java
public class LoginRequest {
    private String email;
    private String password;
    private double latitude;    // new
    private double longitude;   // new

    public LoginRequest(
            String email,
            String password,
            double latitude,
            double longitude
    ) {
        this.email     = email;
        this.password  = password;
        this.latitude  = latitude;
        this.longitude = longitude;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}

