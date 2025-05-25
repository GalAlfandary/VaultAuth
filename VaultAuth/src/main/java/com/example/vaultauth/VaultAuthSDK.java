package com.example.vaultauth;

import com.example.vaultauth.model.AccessTokenResponse;
import com.example.vaultauth.model.LoginRequest;
import com.example.vaultauth.model.LoginResponse;
import com.example.vaultauth.model.RegisterRequest;
import com.example.vaultauth.model.RegisterResponse;
import com.example.vaultauth.model.UserResponse;

import java.util.concurrent.TimeUnit;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VaultAuthSDK {
    private static final String DEFAULT_BASE_URL = "https://flask-jade-tau.vercel.app/";
    private final AuthApi api;
    private final String baseUrl;

    /**
     * Creates a new VaultAuthSDK instance with the default base URL.
     */
    public VaultAuthSDK() {
        this(DEFAULT_BASE_URL);
    }

    /**
     * Creates a new VaultAuthSDK instance with a custom base URL.
     *
     * @param baseUrl the base URL for the API
     */
    public VaultAuthSDK(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(AuthApi.class);
    }

    /**
     * Register a new user.
     *
     * @param email user's email
     * @param password user's password
     * @param name user's name
     * @return a Call object that can be executed to perform the registration
     */
    public Call<RegisterResponse> register(String email, String password, String name) {
        return api.register(new RegisterRequest(email, password, name));
    }

    /**
     * Log in an existing user with location tracking.
     *
     * This method sends the user's credentials and optional location
     * to the backend login API and returns a Retrofit Call object.
     *
     * @param loginRequest an object containing the user's email, password, and location
     * @return a Call object that can be enqueued or executed to perform the login
     */
    public Call<LoginResponse> login(LoginRequest loginRequest) {
        return api.login(loginRequest);
    }




    /**
     * Get user information.
     *
     * @param accessToken JWT access token
     * @return a Call object that can be executed to get user information
     */
    public Call<UserResponse> getUser(String accessToken) {
        return api.getUser(formatAuthHeader(accessToken));
    }

    /**
     * Refresh an access token using a refresh token.
     *
     * @param refreshToken JWT refresh token
     * @return a Call object that can be executed to refresh the token
     */
    public Call<AccessTokenResponse> refresh(String refreshToken) {
        return api.refresh(formatAuthHeader(refreshToken));
    }

    /**
     * Format the authorization header value.
     *
     * @param token the JWT token
     * @return formatted authorization header value
     */
    private String formatAuthHeader(String token) {
        if (token.startsWith("Bearer ")) {
            return token;
        }
        return "Bearer " + token;
    }

    /**
     * Get the base URL used by this SDK instance.
     *
     * @return the base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }
}