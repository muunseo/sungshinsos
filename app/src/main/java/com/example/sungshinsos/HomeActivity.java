package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    // 추가된 그리드 이미지 뷰 변수들
    private ImageView imageViewChatbot2;
    private ImageView imageViewMypage2;
    private ImageView imageViewMap2;
    private ImageView imageViewPost2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ImageView 참조 및 클릭 이벤트 설정 (하단바)
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
        chatbotImageView.setOnClickListener(v -> {
            // ChatBotActivity로 이동
            Intent intent = new Intent(HomeActivity.this, ChatBotActivity.class);
            startActivity(intent);
        });

        // GridLayout의 ImageView 참조 및 클릭 이벤트 설정
        imageViewChatbot2 = findViewById(R.id.imageView_chatbot2);
        imageViewChatbot2.setOnClickListener(v -> {
            // ChatBotActivity로 이동
            Intent intent = new Intent(HomeActivity.this, ChatBotActivity.class);
            startActivity(intent);
        });

        imageViewMypage2 = findViewById(R.id.imageView_mypage2);
        imageViewMypage2.setOnClickListener(v -> {
            // MyPageActivity로 이동
            Intent intent = new Intent(HomeActivity.this, MyPageActivity.class);
            startActivity(intent);
        });

        imageViewMap2 = findViewById(R.id.imageView_map2);
        imageViewMap2.setOnClickListener(v -> {
            // MapActivity로 이동
            Intent intent = new Intent(HomeActivity.this, MapActivity.class);
            startActivity(intent);
        });

        imageViewPost2 = findViewById(R.id.imageView_post2);
        imageViewPost2.setOnClickListener(v -> {
            // PostActivity로 이동
            Intent intent = new Intent(HomeActivity.this, PostActivity.class);
            startActivity(intent);
        });

        // TextView 참조 (하단바)
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
}
