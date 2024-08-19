package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyUserActivity extends AppCompatActivity {

    private EditText etPassword;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);

        etPassword = findViewById(R.id.etVerifyPassword);
        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(v -> {
            verifyUser();
        });
    }

    private void verifyUser() {
        String password = etPassword.getText().toString().trim();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // 인증 성공 시 EditUserInfoActivity로 이동
                    Intent intent = new Intent(VerifyUserActivity.this, EditUserInfoActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(VerifyUserActivity.this, "정보 수정을 실패했습니다. 비밀번호 확인 후 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
