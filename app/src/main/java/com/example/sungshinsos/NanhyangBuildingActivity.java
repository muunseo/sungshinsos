package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class NanhyangBuildingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanhyang_building);

        Button buttonFloor5 = findViewById(R.id.button_floor_5);
        Button buttonFloor6 = findViewById(R.id.button_floor_6);
        Button buttonFloor7 = findViewById(R.id.button_floor_7);
        Button buttonFloor8 = findViewById(R.id.button_floor_8);


        buttonFloor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NanhyangBuildingActivity.this, NanFloor5Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NanhyangBuildingActivity.this, NanFloor6Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NanhyangBuildingActivity.this, NanFloor7Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NanhyangBuildingActivity.this, NanFloor8Activity.class);
                startActivity(intent);
            }
        });
    }
}
