package com.example.sungshinsos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    private Button btnNotificationSound, btnUpdateUserInfo, btnLogout;

    private static final String PREFERENCES_NAME = "login_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        btnNotificationSound = findViewById(R.id.btnNotificationSound);
        btnUpdateUserInfo = findViewById(R.id.btnUpdateUserInfo);
        btnLogout = findViewById(R.id.btnLogout);

        btnNotificationSound.setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, SoundPickerActivity.class);
            startActivity(intent);
        });

        btnUpdateUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, VerifyUserActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            logout();
        });
    }

    private void logout() {
        // SharedPreferences에서 로그인 정보 삭제
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // 로그인 화면으로 이동
        Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
