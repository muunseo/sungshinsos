package com.example.sungshinsos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SujungBuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sujung_building);

        Button buttonFloor1 = findViewById(R.id.button_floor_1);
        Button buttonFloor2 = findViewById(R.id.button_floor_2);
        Button buttonFloor3 = findViewById(R.id.button_floor_3);
        Button buttonFloor4 = findViewById(R.id.button_floor_4);
        Button buttonFloor5a = findViewById(R.id.button_floor_5a);
        Button buttonFloor5b = findViewById(R.id.button_floor_5b);
        Button buttonFloor5c = findViewById(R.id.button_floor_5c);
        Button buttonFloor6a = findViewById(R.id.button_floor_6a);
        Button buttonFloor6b = findViewById(R.id.button_floor_6b);
        Button buttonFloor6c = findViewById(R.id.button_floor_6c);
        Button buttonFloor7a = findViewById(R.id.button_floor_7a);
        Button buttonFloor7b = findViewById(R.id.button_floor_7b);
        Button buttonFloor7c = findViewById(R.id.button_floor_7c);
        Button buttonFloor8a = findViewById(R.id.button_floor_8a);
        Button buttonFloor8b = findViewById(R.id.button_floor_8b);
        Button buttonFloor8c = findViewById(R.id.button_floor_8c);
        Button buttonFloor9a = findViewById(R.id.button_floor_9a);
        Button buttonFloor9b = findViewById(R.id.button_floor_9b);
        Button buttonFloor9c = findViewById(R.id.button_floor_9c);
        Button buttonFloor10a = findViewById(R.id.button_floor_10a);
        Button buttonFloor10b = findViewById(R.id.button_floor_10b);
        Button buttonFloor10c = findViewById(R.id.button_floor_10c);

        // Set onClick listeners for the buttons
        buttonFloor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungFloor1Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungFloor2Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungFloor3Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungFloor4Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungAFloor5Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungBFloor5Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor5c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungCFloor5Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor6a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungAFloor6Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor6b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungBFloor6Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor6c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungCFloor6Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor7a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungAFloor7Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor7b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungBFloor7Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor7c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungCFloor7Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor8a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungAFloor8Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor8b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungBFloor8Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor8c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this,SujungCFloor8Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor9a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungAFloor9Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor9b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungBFloor9Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor9c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungCFloor9Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor10a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungAFloor10Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor10b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungBFloor10Activity.class);
                startActivity(intent);
            }
        });

        buttonFloor10c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujungBuildingActivity.this, SujungCFloor10Activity.class);
                startActivity(intent);
            }
        });
    }
}
