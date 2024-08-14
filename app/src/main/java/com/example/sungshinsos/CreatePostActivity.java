package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class CreatePostActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonPost;
    private TextView textViewAuthor;
    private String postId;
    private String userId;
    private String username; // 비동기적으로 설정할 변수

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonPost = findViewById(R.id.buttonPost);
        textViewAuthor = findViewById(R.id.textViewAuthor);

        // Firebase 초기화
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");

        if (postId != null) {
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            editTextTitle.setText(title);
            editTextContent.setText(content);
        }

        // 현재 로그인한 사용자 정보를 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();

            mDatabaseRef.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            username = user.getName();
                            textViewAuthor.setText("Author: " + username);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("CreatePostActivity", "Failed to load user data.", databaseError.toException());
                }
            });
        }

        buttonPost.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String content = editTextContent.getText().toString();

            if (title.isEmpty() || content.isEmpty() || username == null) {
                Toast.makeText(this, "제목, 내용 또는 사용자 정보가 입력되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                Log.e("CreatePostActivity", "Title, content, or username is empty.");
                return;
            }

            // 서버로 게시물 전송
            sendPostToServer(title, content, userId);
        });
    }

    public void sendPostToServer( String title, String content, String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // 서버 URL로 변경
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        PostService service = retrofit.create(PostService.class);

        // Post 객체 생성
        Post post = new Post(title, content, userId);

        Call<PostResponse> call = service.createPost(post);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("CreatePostActivity", "Post created successfully: " + response.body().getPostId());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("title", title);
                    returnIntent.putExtra("content", content);
                    returnIntent.putExtra("postId", response.body().getPostId()); // Add postId to returnIntent
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    Log.e("CreatePostActivity", "Failed to create post: " + response.errorBody().toString());
                    Toast.makeText(CreatePostActivity.this, "Failed to create post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("CreatePostActivity", "Request failed", t);
                Toast.makeText(CreatePostActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface PostService {
        @POST("/post-created")
        Call<PostResponse> createPost(@Body Post post);
    }
}
