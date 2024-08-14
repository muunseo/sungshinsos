package com.example.sungshinsos;

public class Comment {
    private String commentId;
    private String postId;
    private String username; // 작성자의 username
    private String commentText;
    private String userId;
    private long timestamp;

    // 기본 생성자
    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    // 모든 필드를 포함하는 생성자
    public Comment(String commentId, String postId, String username, String commentText, String userId, long timestamp) {
        this.commentId = commentId;
        this.postId = postId;
        this.username = username;
        this.commentText = commentText;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // 추가된 생성자
    public Comment(String postId, String commentText, String userId, String username){
        this.postId = postId;
        this.commentText = commentText;
        this.userId = userId;
        this.username = username;
        this.timestamp = System.currentTimeMillis(); // 현재 시간을 기본값으로 설정
    }

    // Getter 및 Setter 메소드
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
