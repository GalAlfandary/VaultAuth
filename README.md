# VaultAuth Seminar Project

> **Auth SDK + Admin Portal**  
> A full-stack demonstration of a RESTful authentication service (Flask + MongoDB Atlas), an Android SDK wrapper (Java), and a React-based administration portal with real-time analytics and location tracking.

---

## 🌐 Architecture

```text
┌────────────────────┐      ┌────────────────────┐      ┌─────────────────────┐
│                    │ HTTP │                    │ HTTP │                     │
│   Android App      ├─────▶│   Flask API Service ├─────▶│   MongoDB Atlas     │
│ (VaultAuth SDK)    │      │  (JWT, CORS, Swagger)│      │   (users, logins)   │
│                    │◀─────┤                    │◀─────┤                     │
└────────────────────┘      └────────────────────┘      └─────────────────────┘
                                    ▲
                                    │
                          React Admin Portal
                        (CRUD + Analytics + Map)

```
✨ Features
----------

-   **Secure Authentication**

    -   Password hashing with Werkzeug

    -   JWT access & refresh tokens

-   **Geo-Tracking**

    -   Records latitude/longitude on each login

    -   Reverse-geocodes to city/country

-   **Admin Dashboard**

    -   User management (Create, Read, Delete)

    -   Analytics: total logins, distribution by location

    -   Interactive map & charts

-   **Easy Integration**

    -   Android library published via JitPack

    -   RESTful API can be consumed by any platform

📦 Components
-------------

### 1\. API Service (Flask)

-   **Endpoints**

    -   `POST /register` --- Create new user

    -   `POST /login` --- Authenticate + track location

    -   `POST /refresh` --- Refresh JWT

    -   `GET /verify` --- Validate token

    -   `GET /users` --- List users

    -   `DELETE /users/<id>` --- Remove user

    -   `GET /analytics` --- Logins in last hour by geo

-   **Stack**: Python 3.9, Flask, Flask-JWT-Extended, PyMongo, MongoDB Atlas

-   **Docs**: Swagger auto-generated at `/apidocs`

-   **Deploy**: Vercel.

  ### 2\. Android SDK (VaultAuth)

-   **Language**: Java

-   **Distribution**: JitPack (add `maven { url 'https://jitpack.io' }`)

-   **Key Classes**

    -   `VaultAuthSDK` --- Retrofit client for endpoints

    -   `VaultAuth` --- Singleton wrapper with async location fetch

    -   `model/*` --- POJOs (`LoginRequest`, `RegisterResponse`, etc.)

-   **Installation**

    ```groovy

 
    // settings.gradle
    dependencyResolutionManagement {
      repositories { mavenCentral(); maven { url 'https://jitpack.io' } }
    }

    // module build.gradle
    dependencies {
      implementation 'com.github.YourUser:VaultAuth:1.0.0'
    }
    ```

-   **Usage**

    ```java

    // Use VaultAuth for login
    VaultAuth.getInstance()
        .login("user@example.com", "mypassword", new AuthCallback<User>() {
            @Override
            public void onSuccess(User user) {
                // Called when login succeeds
                runOnUiThread(() -> {
                    Toast.makeText(context,
                        "Welcome " + user.name + "!",
                        Toast.LENGTH_LONG).show();
    
                    // Navigate to welcome activity
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    intent.putExtra("userName", user.name);
                    startActivity(intent);
    
                    // Clear password field for security
                    editPassword.setText("");
                });
            }
    
            @Override
            public void onError(String errorMessage) {
                // Called on failure
                runOnUiThread(() -> {
                    Toast.makeText(context,
                        "Login failed: " + errorMessage,
                        Toast.LENGTH_LONG).show();
                });
            }
        });
    ```

    
    ```java
    // Use VaultAuth for signup
    VaultAuth.getInstance()
        .register("user@example.com", "mypassword", "John Doe",
            new AuthCallback<String>() {
                @Override
                public void onSuccess(String userId) {
                    // Called when account creation succeeds
                    runOnUiThread(() -> {
                        Toast.makeText(context,
                            "Account created (ID: " + userId + ")! Please sign in.",
                            Toast.LENGTH_LONG).show();
                        // e.g. navigate to login screen
                    });
                }
    
                @Override
                public void onError(String errorMessage) {
                    // Called on failure
                    runOnUiThread(() -> {
                        Toast.makeText(context,
                            "Signup failed: " + errorMessage,
                            Toast.LENGTH_LONG).show();
                    });
                }
            }
        );
    
    ```

  ### 3\. Admin Portal (React)
  
  -   **Libraries:** React, Axios, Leaflet, react-chartjs-2
  
  -   **Features:**
  
      -   CRUD on users
  
      -   Real-time login analytics (charts, maps)
  
      -   Geo-reverse-lookup via OpenStreetMap/Nominatim    
