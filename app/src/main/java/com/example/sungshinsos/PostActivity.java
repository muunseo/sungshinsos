package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity implements PostAdapter.OnPostDeleteListener, PostAdapter.OnPostEditListener {
    private static final int REQUEST_CODE_CREATE_POST = 1;
    private static final int REQUEST_CODE_EDIT_POST = 2;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser; // 현재 로그인된 사용자를 나타내는 FirebaseUser 객체
    private String currentUsername; // 현재 로그인된 사용자의 username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Firebase 인스턴스 초기화
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser(); // currentUser를 멤버 변수로 설정

        // 현재 로그인된 사용자의 username 가져오기
        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        currentUsername = user.getName();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("PostActivity", "Failed to load user data.", databaseError.toException());
                }
            });
        }

        // 게시물 관련 초기화
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, this, this, this);
        recyclerViewPosts.setAdapter(postAdapter);

        // Firebase 데이터베이스 레퍼런스 가져오기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Post");

        // 데이터베이스에 있는 게시물 데이터를 읽어오는 리스너 추가
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PostActivity", "Failed to read value.", databaseError.toException());
            }
        });

        FloatingActionButton fabAddPost = findViewById(R.id.fabAddPost);
        fabAddPost.setOnClickListener(v -> {
            Intent intent = new Intent(PostActivity.this, CreatePostActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE_POST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATE_POST || requestCode == REQUEST_CODE_EDIT_POST) {
            // 강제로 데이터베이스에서 게시물 목록을 다시 읽어옴
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        if (post != null) {
                            postList.add(post);
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("PostActivity", "Failed to read value.", databaseError.toException());
                }
            });

        }
    }

    @Override
    public void onPostDelete(int position) {
        // 리스트의 범위를 벗어난 인덱스 체크
        if (position >= 0 && position < postList.size()) {
            Post post = postList.get(position);
            if (post != null) {
                databaseReference.child(post.getPostId()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Log.d("PostActivity", "게시글이 성공적으로 삭제되었습니다.");
                            postList.remove(position);
                            postAdapter.notifyItemRemoved(position);
                        })
                        .addOnFailureListener(e -> {
                            Log.e("PostActivity", "Failed to delete post.", e);
                        });
            }
        } else {
            Log.e("PostActivity", "Invalid position: " + position);
        }
    }

    @Override
    public void onPostEdit(int position) {
        Post post = postList.get(position);
        if (post != null) {
            Intent intent = new Intent(this, EditPostActivity.class);
            intent.putExtra("postId", post.getPostId());
            intent.putExtra("title", post.getTitle());
            intent.putExtra("content", post.getContent());
            startActivityForResult(intent, REQUEST_CODE_EDIT_POST);
        }
    }
}
