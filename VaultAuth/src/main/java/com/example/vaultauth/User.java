package com.example.vaultauth;

public class User {
    public String id;
    public String email;
    public String name;

    public User() {
        // Default constructor required for Retrofit/Gson
    }



    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    // Optional: Getters and Setters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
