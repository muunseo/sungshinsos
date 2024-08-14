package com.example.sungshinsos;

public class User {
    private String name;
    private String email;
    private String fcmToken;
    private NotificationSettings notificationSettings;

    // 기본 생성자
    public User() {
    }

    public User(String name, String email, String fcmToken, NotificationSettings notificationSettings) {
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.notificationSettings = notificationSettings;
    }

    // Getter 및 Setter 메서드
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public NotificationSettings getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(NotificationSettings notificationSettings) {
        this.notificationSettings = notificationSettings;
    }
}

class NotificationSettings {
    private boolean notificationsEnabled;
    private boolean postCreateEnabled;
    private boolean postUpdateEnabled;
    private boolean commentCreateEnabled;
    private String postCreateRingtone;
    private String postUpdateRingtone;
    private String commentCreateRingtone;

    // 기본 생성자
    public NotificationSettings() {
    }

    // 필요한 경우 생성자 추가
    public NotificationSettings(boolean notificationsEnabled, boolean postCreateEnabled, boolean postUpdateEnabled,
                                boolean commentCreateEnabled, String postCreateRingtone, String postUpdateRingtone,
                                String commentCreateRingtone) {
        this.notificationsEnabled = notificationsEnabled;
        this.postCreateEnabled = postCreateEnabled;
        this.postUpdateEnabled = postUpdateEnabled;
        this.commentCreateEnabled = commentCreateEnabled;
        this.postCreateRingtone = postCreateRingtone;
        this.postUpdateRingtone = postUpdateRingtone;
        this.commentCreateRingtone = commentCreateRingtone;
    }

    // Getter 및 Setter 메서드
    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean isPostCreateEnabled() {
        return postCreateEnabled;
    }

    public void setPostCreateEnabled(boolean postCreateEnabled) {
        this.postCreateEnabled = postCreateEnabled;
    }

    public boolean isPostUpdateEnabled() {
        return postUpdateEnabled;
    }

    public void setPostUpdateEnabled(boolean postUpdateEnabled) {
        this.postUpdateEnabled = postUpdateEnabled;
    }

    public boolean isCommentCreateEnabled() {
        return commentCreateEnabled;
    }

    public void setCommentCreateEnabled(boolean commentCreateEnabled) {
        this.commentCreateEnabled = commentCreateEnabled;
    }

    public String getPostCreateRingtone() {
        return postCreateRingtone;
    }

    public void setPostCreateRingtone(String postCreateRingtone) {
        this.postCreateRingtone = postCreateRingtone;
    }

    public String getPostUpdateRingtone() {
        return postUpdateRingtone;
    }

    public void setPostUpdateRingtone(String postUpdateRingtone) {
        this.postUpdateRingtone = postUpdateRingtone;
    }

    public String getCommentCreateRingtone() {
        return commentCreateRingtone;
    }

    public void setCommentCreateRingtone(String commentCreateRingtone) {
        this.commentCreateRingtone = commentCreateRingtone;
    }
}
