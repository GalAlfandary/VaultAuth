package com.example.vaultauth;

import android.util.Log;

import com.example.vaultauth.model.LoginRequest;
import com.example.vaultauth.model.LoginResponse;
import com.example.vaultauth.model.RegisterResponse;
import com.example.vaultauth.model.UserResponse;


import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaultAuth {

    private static VaultAuth instance;
    private final VaultAuthSDK sdk;

    private VaultAuth() {
        sdk = new VaultAuthSDK();
    }

    public static VaultAuth getInstance() {
        if (instance == null) {
            instance = new VaultAuth();
        }
        return instance;
    }

    public void login(String email, String password, AuthCallback<User> callback) {
        fetchLocation((lat, lng) -> {
            LoginRequest req = new LoginRequest(email, password, lat, lng);
            sdk.login(req).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body().user);
                    } else {
                        callback.onError("Login failed");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    callback.onError(t.getMessage());
                }
            });
        });
    }


    public void register(String email, String password, String name, AuthCallback<String> callback) {
        sdk.register(email, password, name).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().user_id);
                } else {
                    callback.onError("Signup failed");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getUser(String accessToken, AuthCallback<User> callback) {
        sdk.getUser(accessToken).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().user);
                } else {
                    callback.onError("User not found");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    private void fetchLocation(LocationCallback callback) {
        new Thread(() -> {
            double lat = 0.0, lng = 0.0;
            HttpURLConnection conn = null;
            try {
                // 1) Primary: ipapi.co (HTTPS)
                URL primary = new URL("https://ipapi.co/json/");
                conn = (HttpURLConnection) primary.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5_000);
                conn.setReadTimeout(5_000);

                int code = conn.getResponseCode();
                Log.d("VaultAuthSDK", "IPAPI response code: " + code);

                InputStream in = (code >= 200 && code < 300)
                        ? conn.getInputStream()
                        : conn.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder payload = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) payload.append(line);
                reader.close();
                conn.disconnect();

                if (code == 429) {
                    Log.w("VaultAuthSDK", "Primary API rate-limited, falling back to ipwhois.app");
                    // 2) Fallback: ipwhois.app (HTTPS, no key)
                    URL fallback = new URL("https://ipwhois.app/json/");
                    conn = (HttpURLConnection) fallback.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5_000);
                    conn.setReadTimeout(5_000);

                    code = conn.getResponseCode();
                    Log.d("VaultAuthSDK", "Fallback response code: " + code);

                    in = (code >= 200 && code < 300)
                            ? conn.getInputStream()
                            : conn.getErrorStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    payload = new StringBuilder();
                    while ((line = reader.readLine()) != null) payload.append(line);
                    reader.close();
                    conn.disconnect();

                    if (code >= 200 && code < 300) {
                        JSONObject obj = new JSONObject(payload.toString());
                        lat = obj.optDouble("latitude", 0.0);
                        lng = obj.optDouble("longitude", 0.0);
                    } else {
                        Log.e("VaultAuthSDK", "Fallback error payload: " + payload);
                    }
                } else if (code >= 200 && code < 300) {
                    // Parse primary response
                    JSONObject obj = new JSONObject(payload.toString());
                    lat = obj.optDouble("latitude", 0.0);
                    lng = obj.optDouble("longitude", 0.0);
                } else {
                    Log.e("VaultAuthSDK", "Primary error payload: " + payload);
                }

                Log.d("VaultAuthSDK", "Final coords: " + lat + ", " + lng);

            } catch (Exception e) {
                Log.e("VaultAuthSDK", "🚨 Failed to fetch location", e);
            } finally {
                if (conn != null) conn.disconnect();
                // Always invoke the callback
                callback.onLocationReceived(lat, lng);
            }
        }).start();
    }





    private interface LocationCallback {
        /**
         * Called when we have the user’s geo-coordinates.
         *
         * @param latitude  the latitude from the IP lookup
         * @param longitude the longitude from the IP lookup
         */
        void onLocationReceived(double latitude, double longitude);
    }


}
