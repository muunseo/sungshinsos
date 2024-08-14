package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageView imageViewMyPage;
    private ImageView postImageView;
    private ImageView mapImageView;
    private TextView chatbotTitle;
    private TextView postTitle;
    private TextView mapTitle;
    private TextView mypageTitle;
    private ImageView chatbotImageView;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ImageView 참조 및 클릭 이벤트 설정
        mapImageView = findViewById(R.id.imageView11);
        mapImageView.setOnClickListener(v -> {
            // MapActivity 시작
            Intent intent = new Intent(HomeActivity.this, MapActivity.class);
            startActivity(intent);
        });

        imageViewMyPage = findViewById(R.id.imageView9);
        imageViewMyPage.setOnClickListener(v -> {
            // MyPageActivity로 이동
            Intent intent = new Intent(HomeActivity.this, MyPageActivity.class);
            startActivity(intent);
        });

        postImageView = findViewById(R.id.imageView5);
        postImageView.setOnClickListener(v -> {
            // PostActivity로 이동
            Intent intent = new Intent(HomeActivity.this, PostActivity.class);
            startActivity(intent);
        });
        chatbotImageView = findViewById(R.id.imageView);
        chatbotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ChatBotActivity로 이동
                Intent intent = new Intent(HomeActivity.this, ChatBotActivity.class);
                startActivity(intent);
            }
        });


        // TextView 참조
        chatbotTitle = findViewById(R.id.chatbot_title);
        postTitle = findViewById(R.id.post_title);
        mapTitle = findViewById(R.id.map_title);
        mypageTitle = findViewById(R.id.mypage_title);

        // 텍스트 설정 (필요한 경우)
        chatbotTitle.setText("sos 챗봇");
        postTitle.setText("sos 게시판");
        mapTitle.setText("sos 지도");
        mypageTitle.setText("마이페이지");
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
}
