package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private DBhelper db;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBhelper(this);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(view -> finish());

        btnLogin.setOnClickListener(view -> handleLogin());
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!isValidInput(email, password)) {
            return;
        }

        if (db.validateUser(email, password)) {
            Intent intent = new Intent(LoginActivity.this, BerandaActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Email atau password salah. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(String email, String password) {
        if (email.isEmpty()) {
            etEmail.setError("Email tidak boleh kosong!");
            etEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Format email tidak valid!");
            etEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password tidak boleh kosong!");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }
}