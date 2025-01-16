package com.example.myjne;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {
    private View[] lineSteps;
    private TextView tvPrice;
    private Handler handler = new Handler();
    private int stepIndex = 0;
    private ImageView ic_chat;
    private static final long ANIMATION_DELAY = 8000; // 8 detik
    private static final long TRANSITION_DELAY = 5000; // 5 detik
    private static final String TAG = "OrderActivity"; // Untuk log debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initializeViews();
        setupClickListeners();
        handleIntent();
        startLineAnimation();
    }

    private void initializeViews() {
        try {
            ic_chat = findViewById(R.id.ic_chat);
            lineSteps = new View[]{
                    findViewById(R.id.line_order_created),
                    findViewById(R.id.line_finding_driver),
                    findViewById(R.id.line_driver_assigned),
                    findViewById(R.id.line_order_picked_up)
            };
            tvPrice = findViewById(R.id.tv_price);

            // Validasi semua komponen
            if (ic_chat == null || tvPrice == null || lineSteps == null) {
                throw new NullPointerException("One or more views are not properly initialized!");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing views: " + e.getMessage());
        }
    }

    private void setupClickListeners() {
        if (ic_chat != null) {
            ic_chat.setOnClickListener(v -> {
                Intent intent = new Intent(OrderActivity.this, ChatActivity.class);
                startActivity(intent);
            });
        } else {
            Log.w(TAG, "ic_chat is null, click listener not set!");
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String tvBiaya = intent.getStringExtra("tvBiaya");
            if (tvBiaya != null && !tvBiaya.isEmpty()) {
                tvPrice.setText(tvBiaya);
            } else {
                tvPrice.setText("IDR 0"); // Default value
                Log.w(TAG, "tvBiaya is null or empty, setting default value.");
            }
        } else {
            Log.w(TAG, "Intent is null, no data received.");
        }
    }

    private void startLineAnimation() {
        // Reset semua garis ke warna default (putih)
        for (View line : lineSteps) {
            if (line != null) {
                line.setBackgroundColor(Color.WHITE);
            } else {
                Log.w(TAG, "One of the lineSteps is null!");
            }
        }

        // Animasi muncul dengan jeda 8 detik
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Pastikan stepIndex tidak melebihi jumlah garis
                if (stepIndex < lineSteps.length) {
                    if (lineSteps[stepIndex] != null) {
                        lineSteps[stepIndex].setBackgroundColor(Color.RED);
                        Log.d(TAG, "Animating step: " + stepIndex);
                    } else {
                        Log.w(TAG, "Line step at index " + stepIndex + " is null!");
                    }
                    stepIndex++;

                    // Lanjutkan animasi jika ada langkah berikutnya
                    handler.postDelayed(this, ANIMATION_DELAY);
                } else {
                    Log.d(TAG, "Animation completed. Transitioning to PickedupActivity...");
                    // Pindah ke PickedupActivity setelah jeda
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(OrderActivity.this, PickedupActivity.class);
                        startActivity(intent);
                        finish();
                    }, TRANSITION_DELAY);
                }
            }
        }, ANIMATION_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hentikan semua callback handler saat Activity dihancurkan
        handler.removeCallbacksAndMessages(null);
        Log.d(TAG, "Handler callbacks removed and activity destroyed.");
    }
}
