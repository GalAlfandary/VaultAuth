package com.example.vaultauth;

import com.example.vaultauth.model.AccessTokenResponse;
import com.example.vaultauth.model.LoginRequest;
import com.example.vaultauth.model.LoginResponse;
import com.example.vaultauth.model.RegisterRequest;
import com.example.vaultauth.model.RegisterResponse;
import com.example.vaultauth.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/register")
    Call<RegisterResponse> register(@Body RegisterRequest body);

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest body);

    @POST("/refresh")
    Call<AccessTokenResponse> refresh(@Header("Authorization") String refreshToken);

    @GET("/user")
    Call<UserResponse> getUser(@Header("Authorization") String accessToken);
}
