package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserInfoActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSave;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 현재 사용자 정보를 가져와서 EditText에 설정합니다.
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            etEmail.setText(user.getEmail());
        }

        btnSave.setOnClickListener(v -> updateUserInfo());
    }

    private void updateUserInfo() {
        String password = etPassword.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (!password.isEmpty()) {
                user.updatePassword(password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditUserInfoActivity.this, "비밀번호 수정을 성공했습니다.", Toast.LENGTH_SHORT).show();
                        // 업데이트 후 MyPageActivity로 돌아가기
                        Intent intent = new Intent(EditUserInfoActivity.this, MyPageActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EditUserInfoActivity.this, "비밀번호 수정을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(EditUserInfoActivity.this, "수정할 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
