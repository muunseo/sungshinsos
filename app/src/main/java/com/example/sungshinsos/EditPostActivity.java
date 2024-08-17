package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class EditPostActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSave;
    private String postId;
    private String userId;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        // Firebase 초기화
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        // Intent에서 게시글 데이터 가져오기
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        // EditText에 기존 데이터 설정
        editTextTitle.setText(title);
        editTextContent.setText(content);

        // 현재 로그인한 사용자 정보를 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        buttonSave.setOnClickListener(v -> {
            String updatedTitle = editTextTitle.getText().toString();
            String updatedContent = editTextContent.getText().toString();

            if (updatedTitle.isEmpty() || updatedContent.isEmpty()) {
                Toast.makeText(this, "제목 또는 내용이 수정되지 않았습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 서버로 게시물 업데이트 요청
            updatePostOnServer(postId, updatedTitle, updatedContent, userId);
        });
    }

    public void updatePostOnServer(String postId, String title, String content, String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // 실제 서버 URL로 변경
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        PostService service = retrofit.create(PostService.class);

        // 수정된 Post 객체 생성 (userId는 빈 문자열로 제공, 서버에서 필요로 하는 경우에 맞춰 설정)
        Post post = new Post(postId, title, content, userId);

        // API 호출 시 postId를 문자열로 사용
        Call<PostResponse> call = service.updatePost(post);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("EditPostActivity", "Post updated successfully: " + response.body().getPostId());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("title", title);
                    returnIntent.putExtra("content", content);
                    returnIntent.putExtra("postId", response.body().getPostId());
                    setResult(RESULT_OK, returnIntent);
                    Toast.makeText(EditPostActivity.this, "게시물이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // 서버 응답 내용 로그
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e("EditPostActivity", "Failed to update post: " + errorBody);
                    } catch (IOException e) {
                        Log.e("EditPostActivity", "Failed to read error body", e);
                    }
                    Toast.makeText(EditPostActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("EditPostActivity", "Request failed", t);
                Toast.makeText(EditPostActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface PostService {
        @POST("post-updated") // 서버 엔드포인트에 맞춰 수정
        Call<PostResponse> updatePost(@Body Post post);
    }
}
