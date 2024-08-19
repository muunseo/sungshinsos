package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPassword, etEmail;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseHelper = new FirebaseHelper(); // FirebaseHelper 인스턴스화

        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (password.isEmpty() || email.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "모든 필드를 채워주세요", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(RegisterActivity.this, "이메일 도메인이 @sungshin.ac.kr이어야 합니다.", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "회원 가입 성공", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                String username = getUsernameFromEmail(email); // 이메일에서 학번 추출
                                firebaseHelper.saveUserData(email, username, ""); // 기본값 빈 문자열
                            }
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원 가입 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        String domain = "@sungshin.ac.kr";
        return email.endsWith(domain);
    }

    private String getUsernameFromEmail(String email) {
        return email.split("@")[0]; // @ 앞의 부분을 닉네임으로 사용
    }
}