package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DBhelper db;
    private EditText etNamaLengkap, etEmail, etPassword, etPhoneNumber;
    private Button btnRegister;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi elemen UI dan database helper
        db = new DBhelper(this);
        etNamaLengkap = findViewById(R.id.et_namalengkap);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btn_back);

        // Tombol kembali
        btnBack.setOnClickListener(view -> finish());

        // Tombol registrasi
        btnRegister.setOnClickListener(view -> handleRegister());
    }

    private void handleRegister() {
        String namaLengkap = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Log.d("RegisterActivity", "Input: Nama = " + namaLengkap + ", Email = " + email + ", Password = " + password);

        if (!isValidInput(namaLengkap, email, password)) {
            return;
        }

        if (db.checkUser(email)) {
            Toast.makeText(this, "Email sudah terdaftar, gunakan email lain!", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = db.insertUser(namaLengkap, email, password); // Hapus phone dari parameter
            if (isInserted) {
                Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show();
                redirectToLogin();
            } else {
                Toast.makeText(this, "Registrasi gagal, coba lagi.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean isValidInput(String namaLengkap, String email, String password) {
        // Validasi nama lengkap
        if (namaLengkap.isEmpty()) {
            etNamaLengkap.setError("Nama lengkap tidak boleh kosong!");
            etNamaLengkap.requestFocus();
            return false;
        }

        // Validasi email
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email tidak valid!");
            etEmail.requestFocus();
            return false;
        }

        // Validasi password
        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter!");
            etPassword.requestFocus();
            return false;
        }

        return true; // Nomor telepon tidak divalidasi karena bersifat opsional
    }

    private void redirectToLogin() {
        // Pindah ke halaman login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
