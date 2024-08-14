package com.example.sungshinsos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class PostDetailActivity extends AppCompatActivity {
    private static final String TAG = "PostDetailActivity";
    private TextView textViewPostTitle;
    private TextView textViewPostContent;
    private EditText editTextComment;
    private Button buttonPostComment;
    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    private FirebaseAuth mAuth;
    private String postId;
    private String userId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        textViewPostTitle = findViewById(R.id.textViewPostTitle);
        textViewPostContent = findViewById(R.id.textViewPostContent);
        editTextComment = findViewById(R.id.editTextComment);
        buttonPostComment = findViewById(R.id.buttonPostComment);
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerViewComments.setAdapter(commentAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Post");
        userReference = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        postId = getIntent().getStringExtra("postId");
        Log.d(TAG, "currentPostId: " + postId);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e(TAG, "Current user is null");
            return;
        }
        userId = currentUser.getUid();
        Log.d(TAG, "Fetching data for userId: " + userId);
        userReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    username = user.getName();
                    Log.d(TAG, "Username retrieved: " + username);
                } else {
                    Log.e(TAG, "User data is null for userId: " + userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to load user data.", databaseError.toException());
            }
        });

        databaseReference.child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                if (post != null) {
                    textViewPostTitle.setText(post.getTitle());
                    textViewPostContent.setText(post.getContent());
                } else {
                    Log.e(TAG, "Post is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read post.", databaseError.toException());
            }
        });

        loadComments();

        buttonPostComment.setOnClickListener(v -> {
            String commentText = editTextComment.getText().toString();
            if (commentText.isEmpty()) {
                Log.e(TAG, "commentText is empty");
                return;
            }
            if (postId == null) {
                Log.e(TAG, "currentPostId is null");
                return;
            }
            if (username == null) {
                Log.e(TAG, "username is null");
                return;
            }

            // Unique comment ID 생성
            String commentId = UUID.randomUUID().toString();
            long timestamp = System.currentTimeMillis();
            Comment comment = new Comment( postId, commentText, userId, username);

            // 서버로 댓글 전송
            sendCommentToServer(comment);
        });
    }

    private void sendCommentToServer(Comment comment) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // 서버 URL로 변경
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CommentService service = retrofit.create(CommentService.class);

        Call<CommentResponse> call = service.createComment(comment);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String commentId = response.body().getCommentId();
                    Log.d(TAG, "Comment created successfully: " + commentId);

                    // 서버에서 성공적으로 댓글을 추가한 후에 Firebase에 댓글 추가
                    databaseReference.child(postId).child("comments").child(commentId).setValue(comment)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "댓글이 성공적으로 추가되었습니다.");
                                editTextComment.setText("");
                                loadComments(); // 댓글 목록 업데이트
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "댓글 추가를 실패했습니다.", e);
                            });

                    Toast.makeText(PostDetailActivity.this, "댓글이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e(TAG, "Failed to create comment: " + errorBody);
                        Toast.makeText(PostDetailActivity.this, "댓글 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body", e);
                        Toast.makeText(PostDetailActivity.this, "댓글 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Log.e(TAG, "Request failed", t);
                Toast.makeText(PostDetailActivity.this, "요청 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public interface CommentService {
        @POST("/comment-created")
        Call<CommentResponse> createComment(@Body Comment comment);
    }

    private void loadComments() {
        if (postId == null) {
            Log.e(TAG, "currentPostId is null");
            return;
        }

        databaseReference.child(postId).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    if (comment != null) {
                        commentList.add(comment);
                    } else {
                        Log.e(TAG, "Comment is null");
                    }
                }
                commentAdapter.notifyDataSetChanged();
                // 데이터가 업데이트 된 후, 리사이클러뷰의 스크롤을 최신 댓글로 이동시키기
                if (!commentList.isEmpty()) {
                    recyclerViewComments.smoothScrollToPosition(commentList.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read comments.", databaseError.toException());
            }
        });
    }
}
