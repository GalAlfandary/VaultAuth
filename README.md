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

  ### 3\. [Admin Portal (React)](https://github.com/GalAlfandary/AdminPortal)
  
  -   **Libraries:** React, Axios, Leaflet, react-chartjs-2
  
  -   **Features:**
  
      -   CRUD on users
  
      -   Real-time login analytics (charts, maps)
  
      -   Geo-reverse-lookup via OpenStreetMap/Nominatim
   
  -    **Screenshots:**
    <img width="961" alt="Image" src="https://github.com/user-attachments/assets/3777a28d-3a50-4170-9977-02008c34dd35" />

<img width="1906" alt="Image" src="https://github.com/user-attachments/assets/0e2e3b01-bd54-4489-acf0-988848e895f7" />

<img width="1621" alt="Image" src="https://github.com/user-attachments/assets/685601f0-cfb7-4f5e-ae69-9bd7b94893cd" />

<img width="1886" alt="Image" src="https://github.com/user-attachments/assets/fc8fa676-6193-40a0-be69-3ed36337fcd2" />

<img width="1913" alt="Image" src="https://github.com/user-attachments/assets/cd0b0f85-facf-48bc-9d42-0d104cad990e" />
