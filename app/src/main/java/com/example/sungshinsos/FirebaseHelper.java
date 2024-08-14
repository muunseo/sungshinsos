package com.example.sungshinsos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    public FirebaseHelper() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void saveUserData(String email, String name, String fcmToken) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = mDatabaseRef.child("users").child(userId);
            userRef.child("email").setValue(email);
            userRef.child("name").setValue(name);
            userRef.child("fcmToken").setValue(fcmToken);
            userRef.child("notificationSettings").child("commentCreateEnabled").setValue(true);
            userRef.child("notificationSettings").child("commentCreateRingtone").setValue("default_ringtone");
            userRef.child("notificationSettings").child("notificationsEnabled").setValue(true);
            userRef.child("notificationSettings").child("postCreateEnabled").setValue(true);
            userRef.child("notificationSettings").child("postCreateRingtone").setValue("default_ringtone");
            userRef.child("notificationSettings").child("postUpdateEnabled").setValue(true);
            userRef.child("notificationSettings").child("postUpdateRingtone").setValue("default_ringtone");
        }
    }

    public void saveToken(String token) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            mDatabaseRef.child("users").child(userId).child("fcmToken").setValue(token);
        }
    }
}
