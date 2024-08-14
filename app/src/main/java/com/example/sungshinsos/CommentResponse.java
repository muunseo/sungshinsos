package com.example.sungshinsos;

public class CommentResponse {
    private String commentId;
    private String message;

    // 기본 생성자
    public CommentResponse() {
    }

    // 모든 필드를 포함하는 생성자
    public CommentResponse(String commentId, String message) {
        this.commentId = commentId;
        this.message = message;
    }

    // Getter 및 Setter 메소드
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
