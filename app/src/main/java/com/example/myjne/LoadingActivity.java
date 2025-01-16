package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private Button btnCancel;
    private Handler handler = new Handler();
    private static final long LOADING_DELAY = 10000; // 5 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Inisialisasi tombol cancel
        btnCancel = findViewById(R.id.btn_cancel);

        // Set click listener untuk tombol cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Batalkan handler untuk menghindari transisi otomatis
                handler.removeCallbacksAndMessages(null);

                // Kembali ke beranda
                Intent intent = new Intent(LoadingActivity.this, BerandaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Menghapus back stack
                startActivity(intent);

                // Tampilkan pesan berhasil
                Toast.makeText(LoadingActivity.this, "Berhasil cancel orderan", Toast.LENGTH_SHORT).show();
                finish(); // Menutup LoadingActivity
            }
        });

        // Menjalankan handler untuk pindah ke OrderActivity setelah 5 detik
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, OrderActivity.class);
                startActivity(intent);
                finish(); // Menutup LoadingActivity
            }
        }, LOADING_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hentikan semua callbacks dari handler ketika activity dihancurkan
        handler.removeCallbacksAndMessages(null);
    }
}
