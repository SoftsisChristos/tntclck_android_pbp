package com.example.tentoclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class People extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        Button btn_goToCustomersScreen = findViewById(R.id.btnID_goToCustomers);
        Button btn_goToPartnersScreen = findViewById(R.id.btnID_goToPartners);

        btn_goToCustomersScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(People.this, Customers.class);
                startActivity(intent);
            }
        });

        btn_goToPartnersScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(People.this, Partners.class);
                startActivity(intent);
            }
        });
    }
}