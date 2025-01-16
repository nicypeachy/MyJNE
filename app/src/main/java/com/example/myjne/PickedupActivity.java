package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PickedupActivity extends AppCompatActivity {
    private Button btnReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickedup);

        // Initialize the button
        btnReturnHome = findViewById(R.id.btn_return_home);

        // Set click listener for return home button
        btnReturnHome.setOnClickListener(v -> {

            Intent intent = new Intent(PickedupActivity.this, BerandaActivity.class);
            // Clear back stack so user can't go back to previous screens
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });



    }
}