package com.example.sungshinsos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SoundPickerActivity extends Activity {

    private Switch enableNotificationsSwitch;
    private Switch postCreateSwitch;
    private Switch postUpdateSwitch;
    private Switch commentCreateSwitch;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_picker);

        sharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE);

        enableNotificationsSwitch = findViewById(R.id.enableNotificationsSwitch);
        postCreateSwitch = findViewById(R.id.postCreateSwitch);
        postUpdateSwitch = findViewById(R.id.postUpdateSwitch);
        commentCreateSwitch = findViewById(R.id.commentCreateSwitch);

        // Load saved settings
        loadSettings();

        enableNotificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            findViewById(R.id.notificationSettingsLayout).setVisibility(isChecked ? View.VISIBLE : View.GONE);
            saveNotificationSettings();  // Save settings when the switch is changed
        });

        postCreateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> saveNotificationSettings());
        postUpdateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> saveNotificationSettings());
        commentCreateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> saveNotificationSettings());
    }

    private void saveNotificationSettings() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            userRef.child("notificationSettings").child("notificationsEnabled").setValue(enableNotificationsSwitch.isChecked());
            userRef.child("notificationSettings").child("postCreateEnabled").setValue(postCreateSwitch.isChecked());
            userRef.child("notificationSettings").child("postUpdateEnabled").setValue(postUpdateSwitch.isChecked());
            userRef.child("notificationSettings").child("commentCreateEnabled").setValue(commentCreateSwitch.isChecked());
        }
    }

    private void loadSettings() {
        boolean notificationsEnabled = sharedPreferences.getBoolean("notificationsEnabled", false);
        enableNotificationsSwitch.setChecked(notificationsEnabled);
        findViewById(R.id.notificationSettingsLayout).setVisibility(notificationsEnabled ? View.VISIBLE : View.GONE);

        postCreateSwitch.setChecked(sharedPreferences.getBoolean("postCreateEnabled", false));
        postUpdateSwitch.setChecked(sharedPreferences.getBoolean("postUpdateEnabled", false));
        commentCreateSwitch.setChecked(sharedPreferences.getBoolean("commentCreateEnabled", false));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notificationsEnabled", enableNotificationsSwitch.isChecked());
        editor.putBoolean("postCreateEnabled", postCreateSwitch.isChecked());
        editor.putBoolean("postUpdateEnabled", postUpdateSwitch.isChecked());
        editor.putBoolean("commentCreateEnabled", commentCreateSwitch.isChecked());
        editor.apply();
        saveNotificationSettings();  // Save settings to Firebase when the activity pauses
    }
}
