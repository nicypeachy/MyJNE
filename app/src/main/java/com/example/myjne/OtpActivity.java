package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    EditText etOtp1, etOtp2, etOtp3, etOtp4;
    Button btnVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kodeotp);

        // Inisialisasi EditText dan Button dari layout
        etOtp1 = findViewById(R.id.et_otp_1);
        etOtp2 = findViewById(R.id.et_otp_2);
        etOtp3 = findViewById(R.id.et_otp_3);
        etOtp4 = findViewById(R.id.et_otp_4);
        btnVerifyOtp = findViewById(R.id.btn_kodeotp);

        // Ambil email dan OTP yang dikirim dari login activity
        final String email = getIntent().getStringExtra("email");
        final int generatedOtp = getIntent().getIntExtra("otp", -1);

        // Tambahkan TextWatcher untuk OTP input
        addOtpTextWatchers();

        // Tombol verifikasi OTP
        btnVerifyOtp.setOnClickListener(view -> {
            // Ambil OTP yang dimasukkan oleh pengguna
            String enteredOtp = etOtp1.getText().toString() + etOtp2.getText().toString() +
                    etOtp3.getText().toString() + etOtp4.getText().toString();

            // Cek apakah OTP yang dimasukkan sesuai dengan yang dihasilkan
            if (enteredOtp.equals(String.valueOf(generatedOtp))) {
                Toast.makeText(OtpActivity.this, "OTP berhasil diverifikasi", Toast.LENGTH_SHORT).show();

                // Pindah ke halaman beranda atau dashboard
                Intent intent = new Intent(OtpActivity.this, BerandaActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(OtpActivity.this, "OTP salah, coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addOtpTextWatchers() {
        // Menambahkan TextWatcher untuk setiap EditText
        etOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) etOtp2.requestFocus();
            }
        });

        etOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) etOtp3.requestFocus();
            }
        });

        etOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) etOtp4.requestFocus();
            }
        });

        etOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
