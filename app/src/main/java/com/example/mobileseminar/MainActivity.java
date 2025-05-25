package com.example.mobileseminar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.snackbar.Snackbar;

import com.example.vaultauth.AuthCallback;
import com.example.vaultauth.User;
import com.example.vaultauth.VaultAuth;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editEmail, editPassword, editName;
    private MaterialButton btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        initViews();

        // Set up click listeners
        setupClickListeners();
    }

    private void initViews() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        btnSignup.setOnClickListener(v -> handleSignup());
    }

    private void handleLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
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

                    // Clear password field for security
                    editPassword.setText("");
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

    private void handleSignup() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Validate inputs
        if (name.isEmpty()) {
            editName.setError("Name is required");
            editName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Password must be at least 6 characters");
            editPassword.requestFocus();
            return;
        }

        // Show loading state
        setSignupLoadingState(true);

        // Use VaultAuth for signup
        VaultAuth.getInstance().register(email, password, name, new AuthCallback<String>() {
            @Override
            public void onSuccess(String userId) {
                runOnUiThread(() -> {
                    setSignupLoadingState(false);
                    showSuccessMessage("Account created successfully! Please sign in.");

                    // Clear name field and focus on login section
                    editName.setText("");
                    editEmail.requestFocus();
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

    private void setLoginLoadingState(boolean isLoading) {
        btnLogin.setEnabled(!isLoading);
        btnLogin.setText(isLoading ? "Signing In..." : "Sign In");

        // Optionally disable other inputs during loading
        if (isLoading) {
            editEmail.setEnabled(false);
            editPassword.setEnabled(false);
            btnSignup.setEnabled(false);
        } else {
            editEmail.setEnabled(true);
            editPassword.setEnabled(true);
            btnSignup.setEnabled(true);
        }
    }

    private void setSignupLoadingState(boolean isLoading) {
        btnSignup.setEnabled(!isLoading);
        btnSignup.setText(isLoading ? "Creating Account..." : "Create Account");

        // Optionally disable other inputs during loading
        if (isLoading) {
            editName.setEnabled(false);
            editEmail.setEnabled(false);
            editPassword.setEnabled(false);
            btnLogin.setEnabled(false);
        } else {
            editName.setEnabled(true);
            editEmail.setEnabled(true);
            editPassword.setEnabled(true);
            btnLogin.setEnabled(true);
        }
    }

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
}