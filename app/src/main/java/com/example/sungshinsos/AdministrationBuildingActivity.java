package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdministrationBuildingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_building);

        Button buttonFloor1 = findViewById(R.id.button_floor_1);
        Button buttonFloor2 = findViewById(R.id.button_floor_2);
        Button buttonFloor3 = findViewById(R.id.button_floor_3);
        Button buttonFloor4 = findViewById(R.id.button_floor_4);
        Button buttonFloor5 = findViewById(R.id.button_floor_5);

        buttonFloor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AdministrationBuildingActivity.this, AdminFloor1Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AdministrationBuildingActivity.this, AdminFloor2Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AdministrationBuildingActivity.this, AdminFloor3Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AdministrationBuildingActivity.this, AdminFloor4Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AdministrationBuildingActivity.this, AdminFloor5Activity.class);
                startActivity(intent);
            }
        });
    }
}
