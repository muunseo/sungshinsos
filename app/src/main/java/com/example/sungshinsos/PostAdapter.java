package com.example.sungshinsos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> postList;
    private Context context;
    private OnPostDeleteListener onPostDeleteListener;
    private OnPostEditListener onPostEditListener;

    public PostAdapter(ArrayList<Post> postList, Context context, OnPostDeleteListener onPostDeleteListener, OnPostEditListener onPostEditListener) {
        this.postList = postList;
        this.context = context;
        this.onPostDeleteListener = onPostDeleteListener;
        this.onPostEditListener = onPostEditListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewContent.setText(post.getContent());

        // 게시글 클릭 시 상세 페이지로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("postId", post.getPostId());
            context.startActivity(intent);
        });

        // 삭제 버튼 클릭 리스너
        holder.buttonDeletePost.setOnClickListener(v -> {
            Log.d("PostAdapter", "Delete button clicked for position: " + position);
            if (onPostDeleteListener != null) {
                onPostDeleteListener.onPostDelete(position);
            }
        });

        // 수정 버튼 클릭 리스너
        holder.buttonEditPost.setOnClickListener(v -> {
            if (onPostEditListener != null) {
                onPostEditListener.onPostEdit(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0; // null 확인
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewContent;
        public Button buttonDeletePost;
        public Button buttonEditPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            buttonDeletePost = itemView.findViewById(R.id.buttonDeletePost);
            buttonEditPost = itemView.findViewById(R.id.buttonEditPost);
        }
    }

    public interface OnPostDeleteListener {
        void onPostDelete(int position);
    }

    public interface OnPostEditListener {
        void onPostEdit(int position);
    }
}
