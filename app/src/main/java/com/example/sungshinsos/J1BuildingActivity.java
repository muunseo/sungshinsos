package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class J1BuildingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j1_building);

        Button buttonFloor1 = findViewById(R.id.button_floor_1);
        Button buttonFloor2 = findViewById(R.id.button_floor_2);
        Button buttonFloor3 = findViewById(R.id.button_floor_3);
        Button buttonFloor4 = findViewById(R.id.button_floor_4);
        Button buttonFloor5 = findViewById(R.id.button_floor_5);
        Button buttonFloor6 = findViewById(R.id.button_floor_6);
        Button buttonFloor7 = findViewById(R.id.button_floor_7);

        buttonFloor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor1Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor2Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor3Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor4Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor5Activity.class);
                startActivity(intent);
            }
        });
        buttonFloor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor6Activity.class);
                startActivity(intent);
            }
        });
        buttonFloor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J1BuildingActivity.this, J1Floor7Activity.class);
                startActivity(intent);
            }
        });
    }
}
