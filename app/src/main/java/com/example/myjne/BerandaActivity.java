package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BerandaActivity extends AppCompatActivity {

    private TextView cekongkir;
    private ImageView logocekongkir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda); // Menghubungkan ke layout activity_beranda

        // Inisialisasi view
        cekongkir = findViewById(R.id.cekongkir);
        logocekongkir = findViewById(R.id.logocekongkir);

        // Tambahkan listener untuk teks "Cek Ongkir"
        cekongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk berpindah ke FormPickupActivity
                Intent intent = new Intent(BerandaActivity.this, FormPickupActivity.class);
                startActivity(intent);
            }
        });

        // Tambahkan listener untuk logo "Cek Ongkir"
        logocekongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk berpindah ke FormPickupActivity
                Intent intent = new Intent(BerandaActivity.this, FormPickupActivity.class);
                startActivity(intent);
            }
        });
    }
}
