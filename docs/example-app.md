# Example Application Guide

This guide demonstrates how to use the VaultAuth library in a real-world Android application. The example app showcases user authentication, registration, and session management.

## Features

- User registration with email and password
- User login with email and password
- Session management
- Error handling and user feedback
- Material Design UI components

## Implementation Details

### 1. Layout Structure

The main activity (`MainActivity.java`) implements a clean and modern UI using Material Design components:

```xml
<!-- activity_main.xml -->
<LinearLayout>
    <TextInputEditText
        android:id="@+id/editEmail"
        android:hint="Email" />
    
    <TextInputEditText
        android:id="@+id/editPassword"
        android:hint="Password"
        android:inputType="textPassword" />
    
    <TextInputEditText
        android:id="@+id/editName"
        android:hint="Name" />
    
    <MaterialButton
        android:id="@+id/btnLogin"
        android:text="Sign In" />
    
    <MaterialButton
        android:id="@+id/btnSignup"
        android:text="Create Account" />
</LinearLayout>
```

### 2. Authentication Implementation

#### Login Implementation

```java
private void handleLogin() {
    String email = editEmail.getText().toString().trim();
    String password = editPassword.getText().toString().trim();

    // Validate inputs
    if (email.isEmpty()) {
        editEmail.setError("Email is required");
        return;
    }

    if (password.isEmpty()) {
        editPassword.setError("Password is required");
        return;
    }

    // Show loading state
    setLoginLoadingState(true);

    // Use VaultAuth for login
    VaultAuth.getInstance().login(email, password, new AuthCallback<User>() {
        @Override
        public void onSuccess(User user) {
            runOnUiThread(() -> {
                setLoginLoadingState(false);
                showSuccessMessage("Welcome " + user.name + "!");
                
                // Navigate to welcome activity
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.putExtra("userName", user.name);
                startActivity(intent);
            });
        }

        @Override
        public void onError(String errorMessage) {
            runOnUiThread(() -> {
                setLoginLoadingState(false);
                showErrorMessage("Login failed: " + errorMessage);
            });
        }
    });
}
```

#### Registration Implementation

```java
private void handleSignup() {
    String name = editName.getText().toString().trim();
    String email = editEmail.getText().toString().trim();
    String password = editPassword.getText().toString().trim();

    // Validate inputs
    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
        showErrorMessage("All fields are required");
        return;
    }

    if (password.length() < 6) {
        editPassword.setError("Password must be at least 6 characters");
        return;
    }

    // Show loading state
    setSignupLoadingState(true);

    // Use VaultAuth for registration
    VaultAuth.getInstance().register(email, password, name, new AuthCallback<String>() {
        @Override
        public void onSuccess(String userId) {
            runOnUiThread(() -> {
                setSignupLoadingState(false);
                showSuccessMessage("Account created successfully!");
            });
        }

        @Override
        public void onError(String errorMessage) {
            runOnUiThread(() -> {
                setSignupLoadingState(false);
                showErrorMessage("Signup failed: " + errorMessage);
            });
        }
    });
}
```

### 3. UI State Management

The example app implements proper loading states and error handling:

```java
private void setLoginLoadingState(boolean isLoading) {
    btnLogin.setEnabled(!isLoading);
    btnLogin.setText(isLoading ? "Signing In..." : "Sign In");
    
    // Disable inputs during loading
    editEmail.setEnabled(!isLoading);
    editPassword.setEnabled(!isLoading);
    btnSignup.setEnabled(!isLoading);
}
```

### 4. Error Handling and User Feedback

The app uses Snackbar for user feedback:

```java
private void showSuccessMessage(String message) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getResources().getColor(R.color.success_color))
            .setTextColor(getResources().getColor(R.color.white))
            .show();
}

private void showErrorMessage(String message) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getResources().getColor(R.color.error_color))
            .setTextColor(getResources().getColor(R.color.white))
            .show();
}
```

## Running the Example

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## Screenshots

[Add screenshots of the app here]

## Best Practices Demonstrated

1. **Input Validation**
   - Client-side validation before API calls
   - Clear error messages
   - User-friendly feedback

2. **UI/UX**
   - Loading states
   - Disabled inputs during operations
   - Material Design components
   - Consistent error handling

3. **Code Organization**
   - Clean separation of concerns
   - Reusable components
   - Proper error handling
   - Thread safety with `runOnUiThread`

4. **Security**
   - Password field masking
   - Secure input handling
   - Proper session management

## Common Issues and Solutions

1. **Network Issues**
   - Implement proper error handling
   - Show user-friendly messages
   - Provide retry options

2. **Input Validation**
   - Validate all inputs before API calls
   - Show clear error messages
   - Prevent multiple submissions

3. **UI State**
   - Handle loading states properly
   - Disable inputs during operations
   - Show appropriate feedback

## Next Steps

1. Implement password reset functionality
2. Add social authentication
3. Implement biometric authentication
4. Add remember me functionality
5. Implement proper session management

## Contributing

Feel free to contribute to the example app by:
1. Adding new features
2. Improving the UI/UX
3. Adding more examples
4. Fixing bugs
5. Improving documentation

## License

This example app is licensed under the MIT License. See the LICENSE file for details. 