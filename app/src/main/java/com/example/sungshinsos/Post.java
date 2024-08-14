package com.example.sungshinsos;
public class Post {
    private String postId;
    private String title;
    private String content;
    private String userId;
    private String username;

    public Post(){

    }

    public Post(String postId, String title, String content, String userId, String username) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.username = username;
    }

    public Post(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Post(String postId, String title, String content, String userId){
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }


    // Getter 및 Setter 메소드
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
