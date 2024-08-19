package com.example.sungshinsos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class MapActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "EmergencyContactsPrefs";
    public static final String CONTACTS_KEY = "EmergencyContacts";
    public static final List<EmergencyContact> defaultContacts = new ArrayList<>();

    static {
        // 기본 비상연락처 초기화
        defaultContacts.add(new EmergencyContact("종합 상황실(수정캠퍼스)", "02-920-2171"));
        defaultContacts.add(new EmergencyContact("종합 상황실(운정캠퍼스)", "02-920-2700"));
        defaultContacts.add(new EmergencyContact("성신건강관리팀(수정캠퍼스 주간)", "02-920-7341"));
        defaultContacts.add(new EmergencyContact("성신건강관리팀(수정캠퍼스 주간)", "02-920-7342"));
        defaultContacts.add(new EmergencyContact("성신건강관리팀(운정캠퍼스 주간)", "02-920-2641"));
    }

    private LinearLayout emergencyContactsContent;
    private boolean isEmergencyContactsVisible = false;
    private EditText editTextName, editTextPhoneNumber;
    private RecyclerView recyclerViewContacts;
    private EmergencyContactsAdapter contactsAdapter;
    private List<EmergencyContact> emergencyContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        emergencyContactsContent = findViewById(R.id.emergencyContactsContent);
        TextView emergencyContactsTitle = findViewById(R.id.emergencyContactsTitle);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        ImageView imageView6 = findViewById(R.id.imageView6); // 수정 캠퍼스 지도 이미지뷰

        emergencyContacts = new ArrayList<>(defaultContacts);
        contactsAdapter = new EmergencyContactsAdapter(this, emergencyContacts);

        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(contactsAdapter);

        loadContacts();  // 액티비티가 생성될 때 연락처를 로드

        emergencyContactsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEmergencyContacts();
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();

                if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                    EmergencyContact newContact = new EmergencyContact(name, phoneNumber);
                    emergencyContacts.add(newContact);
                    contactsAdapter.notifyDataSetChanged();
                    saveContacts();  // 새 연락처가 추가될 때 연락처를 저장
                    clearFields();
                }
            }
        });

        // 이미지뷰 클릭 이벤트 설정
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MapDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveContacts();  // 액티비티가 일시정지될 때 연락처를 저장
    }

    private void toggleEmergencyContacts() {
        if (isEmergencyContactsVisible) {
            emergencyContactsContent.setVisibility(View.GONE);
        } else {
            emergencyContactsContent.setVisibility(View.VISIBLE);
        }
        isEmergencyContactsVisible = !isEmergencyContactsVisible;
    }

    private void clearFields() {
        editTextName.getText().clear();
        editTextPhoneNumber.getText().clear();
    }

    private void saveContacts() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder contactsStringBuilder = new StringBuilder();
        for (EmergencyContact contact : emergencyContacts) {
            if (!defaultContacts.contains(contact)) {
                contactsStringBuilder.append(contact.toString()).append(";");
            }
        }
        editor.putString(CONTACTS_KEY, contactsStringBuilder.toString());
        editor.apply();
    }

    private void loadContacts() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String contactsString = preferences.getString(CONTACTS_KEY, "");

        if (!contactsString.isEmpty()) {
            String[] contactStrings = contactsString.split(";");
            for (String contactString : contactStrings) {
                if (!contactString.isEmpty()) {
                    emergencyContacts.add(EmergencyContact.fromString(contactString));
                }
            }
        }
        contactsAdapter.notifyDataSetChanged();
    }
}
