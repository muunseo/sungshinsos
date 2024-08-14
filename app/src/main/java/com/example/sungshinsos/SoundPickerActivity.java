package com.example.sungshinsos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SoundPickerActivity extends Activity {

    private static final int REQUEST_PICK_RINGTONE_POST_CREATE = 1;
    private static final int REQUEST_PICK_RINGTONE_POST_UPDATE = 2;
    private static final int REQUEST_PICK_RINGTONE_COMMENT_CREATE = 3;

    private Switch enableNotificationsSwitch;
    private Switch postCreateSwitch;
    private Switch postUpdateSwitch;
    private Switch commentCreateSwitch;
    private Button pickPostCreateRingtoneButton;
    private Button pickPostUpdateRingtoneButton;
    private Button pickCommentCreateRingtoneButton;
    private TextView selectedPostCreateRingtoneTextView;
    private TextView selectedPostUpdateRingtoneTextView;
    private TextView selectedCommentCreateRingtoneTextView;

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
        pickPostCreateRingtoneButton = findViewById(R.id.pickPostCreateRingtoneButton);
        pickPostUpdateRingtoneButton = findViewById(R.id.pickPostUpdateRingtoneButton);
        pickCommentCreateRingtoneButton = findViewById(R.id.pickCommentCreateRingtoneButton);
        selectedPostCreateRingtoneTextView = findViewById(R.id.selectedPostCreateRingtoneTextView);
        selectedPostUpdateRingtoneTextView = findViewById(R.id.selectedPostUpdateRingtoneTextView);
        selectedCommentCreateRingtoneTextView = findViewById(R.id.selectedCommentCreateRingtoneTextView);

        // Load saved settings
        loadSettings();

        enableNotificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            boolean visibility = isChecked;
            findViewById(R.id.notificationLabel).setVisibility(visibility ? View.VISIBLE : View.GONE);
            findViewById(R.id.notificationSettingsLayout).setVisibility(visibility ? View.VISIBLE : View.GONE);
            saveNotificationSettings();  // Save settings when the switch is changed
        });

        postCreateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pickPostCreateRingtoneButton.setEnabled(isChecked);
            selectedPostCreateRingtoneTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            saveNotificationSettings();  // Save settings when the switch is changed
        });

        postUpdateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pickPostUpdateRingtoneButton.setEnabled(isChecked);
            selectedPostUpdateRingtoneTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            saveNotificationSettings();  // Save settings when the switch is changed
        });

        commentCreateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pickCommentCreateRingtoneButton.setEnabled(isChecked);
            selectedCommentCreateRingtoneTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            saveNotificationSettings();  // Save settings when the switch is changed
        });

        pickPostCreateRingtoneButton.setOnClickListener(v -> pickRingtone(REQUEST_PICK_RINGTONE_POST_CREATE));
        pickPostUpdateRingtoneButton.setOnClickListener(v -> pickRingtone(REQUEST_PICK_RINGTONE_POST_UPDATE));
        pickCommentCreateRingtoneButton.setOnClickListener(v -> pickRingtone(REQUEST_PICK_RINGTONE_COMMENT_CREATE));
    }

    private void pickRingtone(int requestCode) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri ringtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (ringtoneUri != null) {
                String ringtone = ringtoneUri.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (requestCode) {
                    case REQUEST_PICK_RINGTONE_POST_CREATE:
                        editor.putString("postCreateRingtone", ringtone);
                        selectedPostCreateRingtoneTextView.setText("Selected Ringtone: " + RingtoneManager.getRingtone(this, ringtoneUri).getTitle(this));
                        break;
                    case REQUEST_PICK_RINGTONE_POST_UPDATE:
                        editor.putString("postUpdateRingtone", ringtone);
                        selectedPostUpdateRingtoneTextView.setText("Selected Ringtone: " + RingtoneManager.getRingtone(this, ringtoneUri).getTitle(this));
                        break;
                    case REQUEST_PICK_RINGTONE_COMMENT_CREATE:
                        editor.putString("commentCreateRingtone", ringtone);
                        selectedCommentCreateRingtoneTextView.setText("Selected Ringtone: " + RingtoneManager.getRingtone(this, ringtoneUri).getTitle(this));
                        break;
                }
                editor.apply();
                saveNotificationSettings();  // Save ringtone settings to Firebase
            }
        }
    }

    private void saveNotificationSettings() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            userRef.child("notificationSettings").child("notificationsEnabled").setValue(enableNotificationsSwitch.isChecked());
            userRef.child("notificationSettings").child("postCreateEnabled").setValue(postCreateSwitch.isChecked());
            userRef.child("notificationSettings").child("postUpdateEnabled").setValue(postUpdateSwitch.isChecked());
            userRef.child("notificationSettings").child("commentCreateEnabled").setValue(commentCreateSwitch.isChecked());
            userRef.child("notificationSettings").child("postCreateRingtone").setValue(sharedPreferences.getString("postCreateRingtone", "None"));
            userRef.child("notificationSettings").child("postUpdateRingtone").setValue(sharedPreferences.getString("postUpdateRingtone", "None"));
            userRef.child("notificationSettings").child("commentCreateRingtone").setValue(sharedPreferences.getString("commentCreateRingtone", "None"));
        }
    }

    private void loadSettings() {
        boolean notificationsEnabled = sharedPreferences.getBoolean("notificationsEnabled", false);
        enableNotificationsSwitch.setChecked(notificationsEnabled);
        findViewById(R.id.notificationLabel).setVisibility(notificationsEnabled ? View.VISIBLE : View.GONE);
        findViewById(R.id.notificationSettingsLayout).setVisibility(notificationsEnabled ? View.VISIBLE : View.GONE);

        postCreateSwitch.setChecked(sharedPreferences.getBoolean("postCreateEnabled", false));
        pickPostCreateRingtoneButton.setEnabled(postCreateSwitch.isChecked());
        selectedPostCreateRingtoneTextView.setVisibility(postCreateSwitch.isChecked() ? View.VISIBLE : View.GONE);
        selectedPostCreateRingtoneTextView.setText("Selected Ringtone: " + sharedPreferences.getString("postCreateRingtone", "None"));

        postUpdateSwitch.setChecked(sharedPreferences.getBoolean("postUpdateEnabled", false));
        pickPostUpdateRingtoneButton.setEnabled(postUpdateSwitch.isChecked());
        selectedPostUpdateRingtoneTextView.setVisibility(postUpdateSwitch.isChecked() ? View.VISIBLE : View.GONE);
        selectedPostUpdateRingtoneTextView.setText("Selected Ringtone: " + sharedPreferences.getString("postUpdateRingtone", "None"));

        commentCreateSwitch.setChecked(sharedPreferences.getBoolean("commentCreateEnabled", false));
        pickCommentCreateRingtoneButton.setEnabled(commentCreateSwitch.isChecked());
        selectedCommentCreateRingtoneTextView.setVisibility(commentCreateSwitch.isChecked() ? View.VISIBLE : View.GONE);
        selectedCommentCreateRingtoneTextView.setText("Selected Ringtone: " + sharedPreferences.getString("commentCreateRingtone", "None"));
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
