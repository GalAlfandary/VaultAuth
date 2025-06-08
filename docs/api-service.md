# API Service Documentation

This document provides detailed information about the API service endpoints, authentication, and usage examples.

## Base URL

```
https://api.example.com/v1
```

## Authentication

The API uses token-based authentication. Include the authentication token in the request header:

```
Authorization: Bearer <your_token>
```

## Endpoints

### Authentication

#### Register User

```http
POST /auth/register
```

Request body:
```json
{
    "email": "user@example.com",
    "password": "securepassword",
    "name": "John Doe"
}
```

Response (200 OK):
```json
{
    "userId": "123e4567-e89b-12d3-a456-426614174000",
    "message": "User registered successfully"
}
```

#### Login

```http
POST /auth/login
```

Request body:
```json
{
    "email": "user@example.com",
    "password": "securepassword"
}
```

Response (200 OK):
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "name": "John Doe",
        "email": "user@example.com"
    }
}
```

### User Management

#### Get User Profile

```http
GET /users/profile
```

Response (200 OK):
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Doe",
    "email": "user@example.com",
    "createdAt": "2024-03-20T10:00:00Z"
}
```

#### Update User Profile

```http
PUT /users/profile
```

Request body:
```json
{
    "name": "John Smith",
    "email": "john.smith@example.com"
}
```

Response (200 OK):
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Smith",
    "email": "john.smith@example.com",
    "updatedAt": "2024-03-20T11:00:00Z"
}
```

## Error Responses

The API uses standard HTTP status codes and returns error messages in the following format:

```json
{
    "error": {
        "code": "ERROR_CODE",
        "message": "Human readable error message"
    }
}
```

Common error codes:
- `INVALID_CREDENTIALS`: Invalid email or password
- `USER_EXISTS`: User already exists
- `INVALID_TOKEN`: Invalid or expired token
- `VALIDATION_ERROR`: Invalid request data
- `SERVER_ERROR`: Internal server error

## Rate Limiting

The API implements rate limiting to prevent abuse:
- 100 requests per minute for authenticated users
- 20 requests per minute for unauthenticated users

Rate limit headers:
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1616234400
```

## Security

### Password Requirements

- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one number
- At least one special character

### Token Security

- Tokens expire after 24 hours
- Refresh tokens are provided for longer sessions
- Tokens are invalidated on logout

## Best Practices

1. **Error Handling**
   - Always check for error responses
   - Implement proper retry logic
   - Handle network errors gracefully

2. **Security**
   - Store tokens securely
   - Never log sensitive data
   - Use HTTPS for all requests
   - Implement proper token refresh logic

3. **Performance**
   - Implement request caching where appropriate
   - Handle rate limiting
   - Optimize request payload size

## Example Usage

### Android (Kotlin)

```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.example.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

// Login
apiService.login(LoginRequest(email, password))
    .enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                val token = response.body()?.token
                // Store token and handle successful login
            } else {
                // Handle error
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            // Handle network error
        }
    })
```

### Java

```java
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.example.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build();

ApiService apiService = retrofit.create(ApiService.class);

// Login
apiService.login(new LoginRequest(email, password))
    .enqueue(new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            if (response.isSuccessful()) {
                String token = response.body().getToken();
                // Store token and handle successful login
            } else {
                // Handle error
            }
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            // Handle network error
        }
    });
```

## Support

For API support, please contact:
- Email: api-support@example.com
- Documentation: https://docs.example.com
- Status Page: https://status.example.com

## Versioning

The API uses semantic versioning. The current version is v1.0.0.

## Changelog

### v1.0.0 (2024-03-20)
- Initial release
- Basic authentication endpoints
- User management endpoints

## License

This API documentation is licensed under the MIT License. See the LICENSE file for details. 