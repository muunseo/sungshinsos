package com.example.sungshinsos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class ChatBotActivity extends AppCompatActivity {

    private static final String TAG = "ChatbotActivity";
    private DialogflowClient dialogflowClient;
    private LinearLayout chatLayout;
    private ScrollView chatScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // DialogflowClient 초기화
        dialogflowClient = new DialogflowClient();

        // UI 요소 초기화
        Button sendButton = findViewById(R.id.sendBtn);
        EditText queryEditText = findViewById(R.id.queryEditText);
        chatLayout = findViewById(R.id.chatLayout);
        chatScrollView = findViewById(R.id.chatScrollView);

        sendButton.setOnClickListener(v -> {
            String query = queryEditText.getText().toString().trim();
            if (query.isEmpty()) {
                return; // 빈 쿼리는 처리하지 않음
            }

            // ExecutorService를 사용하여 쿼리 처리
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    String response = dialogflowClient.sendQuery(query);
                    runOnUiThread(() -> {
                        addMessageToChat("User: " + query, true);
                        addMessageToChat(response, false);
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Error during sendQuery: ", e);
                    runOnUiThread(() -> addMessageToChat("Error: " + e.getMessage(), false));
                }
            });

            // 입력 필드 초기화
            queryEditText.setText("");
        });
    }

    // 채팅에 메시지를 추가하는 메서드
    private void addMessageToChat(String message, boolean isUserMessage) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view;
        if (isUserMessage) {
            view = inflater.inflate(R.layout.item_user_message, chatLayout, false);
            TextView textView = view.findViewById(R.id.textViewUserMessage);
            textView.setText(message);
        } else {
            view = inflater.inflate(R.layout.item_bot_message, chatLayout, false);
            TextView textView = view.findViewById(R.id.textViewBotMessage);
            textView.setText(message);
        }
        chatLayout.addView(view);

        // 새로운 메시지가 추가될 때 스크롤을 맨 아래로 이동
        chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }
}
