package com.example.myjne;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private EditText messageInput;
    private Button sendButton;
    private LinearLayout chatContent;
    private ImageView backButton;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize UI elements
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        scrollView = findViewById(R.id.chatContent);
        chatContent = scrollView.findViewById(R.id.chatContentLayout);
        backButton = findViewById(R.id.backButton);

        // Back button functionality
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, OrderActivity.class);
            startActivity(intent);
            finish();
        });

        // Send button functionality
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                addToDatabase(message);
                addChatBubble(message);
                messageInput.setText("");
            }
        });
    }

    private void addToDatabase(String message) {
        // Implement database functionality here
    }

    private void addChatBubble(String message) {
        // Container for the whole bubble including any spacing
        LinearLayout containerLayout = new LinearLayout(this);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        containerParams.setMargins(0, 8, 0, 8);
        containerLayout.setLayoutParams(containerParams);
        containerLayout.setGravity(Gravity.END);

        // The actual bubble
        LinearLayout bubbleLayout = new LinearLayout(this);
        LinearLayout.LayoutParams bubbleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        bubbleParams.gravity = Gravity.END;
        bubbleLayout.setLayoutParams(bubbleParams);
        bubbleLayout.setOrientation(LinearLayout.HORIZONTAL);
        bubbleLayout.setBackgroundResource(R.drawable.background_sendchat);

        // Padding for the bubble
        int paddingDp = 16;
        float density = getResources().getDisplayMetrics().density;
        int paddingPx = (int) (paddingDp * density);
        bubbleLayout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

        // Message text
        TextView messageTextView = new TextView(this);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        messageTextView.setLayoutParams(textParams);
        messageTextView.setText(message);
        messageTextView.setTextColor(getResources().getColor(android.R.color.white));
        messageTextView.setTextSize(14);

        // Add views to their parents
        bubbleLayout.addView(messageTextView);
        containerLayout.addView(bubbleLayout);
        chatContent.addView(containerLayout);

        // Scroll to bottom
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}