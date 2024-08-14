package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SungshinBuildingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sungshin_building);

        Button buttonFloor1 = findViewById(R.id.button_floor_1);
        Button buttonFloor2 = findViewById(R.id.button_floor_2);
        Button buttonFloor3 = findViewById(R.id.button_floor_3);
        Button buttonFloor4 = findViewById(R.id.button_floor_4);
        Button buttonFloor5 = findViewById(R.id.button_floor_5);
        Button buttonFloor6 = findViewById(R.id.button_floor_6);
        Button buttonFloor7 = findViewById(R.id.button_floor_7);
        Button buttonFloor8 = findViewById(R.id.button_floor_8);
        Button buttonFloor9 = findViewById(R.id.button_floor_9);
        Button buttonFloor10 = findViewById(R.id.button_floor_10);

        buttonFloor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor1Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor2Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor3Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor4Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor5Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor6Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor7Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor8Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor9Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SungshinBuildingActivity.this, SungshinFloor10Activity.class);
                startActivity(intent);
            }
        });
    }
}
