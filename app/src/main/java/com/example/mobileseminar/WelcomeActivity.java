package com.example.mobileseminar;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String name = getIntent().getStringExtra("userName");
        TextView textView = findViewById(R.id.textWelcome);
        textView.setText("Welcome, " + name + "!");
    }
}
