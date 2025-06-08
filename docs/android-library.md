# VaultAuth Android Library

VaultAuth is a lightweight Android library that simplifies authentication integration in Android applications. This guide will help you integrate and use the library in your Android project.

## Installation

Add the following to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        // ... other repositories
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.YourUsername:MobileSeminar:Tag")
}
```

## Basic Usage

### 1. Initialize VaultAuth

In your Application class or MainActivity:

```java
// Initialize VaultAuth
VaultAuth.getInstance().initialize(context);
```

### 2. User Registration

```java
VaultAuth.getInstance().register(email, password, name, new AuthCallback<String>() {
    @Override
    public void onSuccess(String userId) {
        // Handle successful registration
    }

    @Override
    public void onError(String errorMessage) {
        // Handle registration error
    }
});
```

### 3. User Login

```java
VaultAuth.getInstance().login(email, password, new AuthCallback<User>() {
    @Override
    public void onSuccess(User user) {
        // Handle successful login
        String userName = user.name;
        // Navigate to main screen or perform other actions
    }

    @Override
    public void onError(String errorMessage) {
        // Handle login error
    }
});
```

## API Reference

### VaultAuth Class

The main class for authentication operations.

#### Methods

- `getInstance()`: Get the singleton instance of VaultAuth
- `initialize(Context context)`: Initialize the library
- `login(String email, String password, AuthCallback<User> callback)`: Perform user login
- `register(String email, String password, String name, AuthCallback<String> callback)`: Register a new user
- `logout()`: Log out the current user

### AuthCallback Interface

Callback interface for authentication operations.

```java
public interface AuthCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
```

### User Class

Represents a user in the system.

```java
public class User {
    public String id;
    public String name;
    public String email;
}
```

## Error Handling

The library provides clear error messages for common scenarios:

- Invalid credentials
- Network errors
- Server errors
- Validation errors

Example error handling:

```java
VaultAuth.getInstance().login(email, password, new AuthCallback<User>() {
    @Override
    public void onSuccess(User user) {
        // Handle success
    }

    @Override
    public void onError(String errorMessage) {
        // Show error to user
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }
});
```

## Best Practices

1. Always initialize VaultAuth in your Application class
2. Handle errors appropriately and show user-friendly messages
3. Implement proper session management
4. Use secure storage for sensitive data
5. Follow Android security best practices

## Example Implementation

See the [Example Application Guide](example-app.md) for a complete implementation example.

## Troubleshooting

Common issues and solutions:

1. **Library not found**
   - Ensure JitPack repository is added
   - Check the dependency version

2. **Authentication fails**
   - Verify network connectivity
   - Check credentials
   - Ensure proper initialization

3. **Callback not working**
   - Verify callback implementation
   - Check for null callbacks

## Support

For issues and feature requests, please create an issue in the GitHub repository.

## License

This library is licensed under the MIT License. See the LICENSE file for details. 