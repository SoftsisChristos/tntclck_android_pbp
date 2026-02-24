package com.example.tentoclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tentoclock.pania.activities.PaniaActivityMain;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_goToPeopleScreen = findViewById(R.id.btnID_goToPeople);
        Button btn_goToMetriseisScreen = findViewById(R.id.btnID_goToMetriseis);
        Button btnNeaMetrisi = findViewById(R.id.button2);
        Button btnPania = findViewById(R.id.buttonPania);
        Button btnAppointments = findViewById(R.id.buttonAppointments);

        btn_goToPeopleScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, People.class);
                startActivity(intent);
            }
        });
        btn_goToMetriseisScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Metriseis.class);
                startActivity(intent);
            }
        });

        btnNeaMetrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MetriseisMenu.class);
                startActivity(intent);
            }
        });

        btnPania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaniaActivityMain.class);
                startActivity(intent);
            }
        });

        btnAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppointmentsActivity.class);
                startActivity(intent);
            }
        });
    }
}