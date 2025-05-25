package com.example.vaultauth;

public interface AuthCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
