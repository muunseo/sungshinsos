package com.example.sungshinsos;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private CheckBox cbRememberMe;
    private FirebaseAuth mAuth;
    private EditText etToken;  // FCM 토큰을 표시할 EditText
    private FirebaseHelper firebaseHelper;


    private static final String PREFERENCES_NAME = "login_pref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 1; // 권한 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML에 정의된 EditText를 참조
        etToken = findViewById(R.id.etToken);
        // FirebaseAuth 초기화
        mAuth = FirebaseAuth.getInstance();
        // FirebaseHelper 인스턴스화
        firebaseHelper = new FirebaseHelper();

        // FCM 토큰 가져오기
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        System.out.println("Fetching FCM registration token failed");
                        return;
                    }
                    // FCM 등록 토큰 가져오기
                    String token = task.getResult();
                    // 토큰을 EditText에 표시
                    etToken.setText(token);
                    firebaseHelper.saveToken(token);
                });

        // XML에 정의된 UI 요소들을 참조
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // 자동 로그인 체크
        checkAutoLogin();
        // 알림 권한 요청
        requestNotificationPermission();

        // 로그인 버튼 클릭 시 동작
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            boolean rememberMe = cbRememberMe.isChecked();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->  {
                            if (task.isSuccessful()) {

                                // 로그인 성공 시 로그인 정보 저장
                                if (rememberMe) {
                                    saveLoginInfo(email, password, rememberMe);
                                } else {
                                    clearLoginInfo();
                                }

                                // 홈 화면으로 이동
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "로그인 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });

        // 회원가입 버튼 클릭 시 동작
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // FirebaseHelper를 사용
    private void saveTokenToDatabase(String token) {
        firebaseHelper.saveToken(token);
    }

    // 자동 로그인 체크
    private void checkAutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);

        if (rememberMe) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, null);
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

            if (savedEmail != null && savedPassword != null) {
                mAuth.signInWithEmailAndPassword(savedEmail, savedPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 자동 로그인 성공 시 홈 화면으로 이동
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "자동 로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    // 로그인 정보 저장
    private void saveLoginInfo(String email, String password, boolean rememberMe) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.apply();
    }

    // 로그인 정보 초기화
    private void clearLoginInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    // 알림 권한 요청 메서드
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여되지 않았으면 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_NOTIFICATION_PERMISSION);
            }
        }
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용되었습니다
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // 권한이 거부되었습니다
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
