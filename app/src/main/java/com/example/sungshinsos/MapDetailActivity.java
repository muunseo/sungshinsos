package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MapDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);

        Button buttonSujungBuilding = findViewById(R.id.buttonSujungBuilding);
        buttonSujungBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, SujungBuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSungsinBuilding = findViewById(R.id.buttonSungsinBuilding);
        buttonSungsinBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, SungshinBuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonMusicBuilding = findViewById(R.id.buttonMusicBuilding);
        buttonMusicBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, MusicBuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonJ1Building = findViewById(R.id.buttonJ1Building);
        buttonJ1Building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, J1BuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonJ2Building = findViewById(R.id.buttonJ2Building);
        buttonJ2Building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, J2BuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonNanhyangBuilding = findViewById(R.id.buttonNanhyangBuilding);
        buttonNanhyangBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, NanhyangBuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonAdministrationBuilding = findViewById(R.id.buttonAdministrationBuilding);
        buttonAdministrationBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, AdministrationBuildingActivity.class);
                startActivity(intent);
            }
        });

        Button buttonLibrary = findViewById(R.id.buttonLibrary);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDetailActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });
    }
}
