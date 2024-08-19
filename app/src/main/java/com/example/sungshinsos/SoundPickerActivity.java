package com.example.sungshinsos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

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
        });

        postCreateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pickPostCreateRingtoneButton.setEnabled(isChecked);
            selectedPostCreateRingtoneTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        postUpdateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pickPostUpdateRingtoneButton.setEnabled(isChecked);
            selectedPostUpdateRingtoneTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        commentCreateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pickCommentCreateRingtoneButton.setEnabled(isChecked);
            selectedCommentCreateRingtoneTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
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
            }
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
        selectedPostCreateRingtoneTextView.setText("Selected Ringtone: " + sharedPreferences.getString("postCreateRingtone", "Default"));

        postUpdateSwitch.setChecked(sharedPreferences.getBoolean("postUpdateEnabled", false));
        pickPostUpdateRingtoneButton.setEnabled(postUpdateSwitch.isChecked());
        selectedPostUpdateRingtoneTextView.setVisibility(postUpdateSwitch.isChecked() ? View.VISIBLE : View.GONE);
        selectedPostUpdateRingtoneTextView.setText("Selected Ringtone: " + sharedPreferences.getString("postUpdateRingtone", "Default"));

        commentCreateSwitch.setChecked(sharedPreferences.getBoolean("commentCreateEnabled", false));
        pickCommentCreateRingtoneButton.setEnabled(commentCreateSwitch.isChecked());
        selectedCommentCreateRingtoneTextView.setVisibility(commentCreateSwitch.isChecked() ? View.VISIBLE : View.GONE);
        selectedCommentCreateRingtoneTextView.setText("Selected Ringtone: " + sharedPreferences.getString("commentCreateRingtone", "Default"));
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
    }
}
