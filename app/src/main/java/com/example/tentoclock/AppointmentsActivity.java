package com.example.tentoclock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.class_models.Appointment;
import com.example.tentoclock.class_models.MetrishActive;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.interfaces.CustomCallback;
import com.example.tentoclock.recycler_adapters.AppointmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;
    private FloatingActionButton fabAddAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.appointments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data list
        appointmentList = new ArrayList<>();

        // Initialize adapter
        adapter = new AppointmentAdapter(appointmentList);
        recyclerView.setAdapter(adapter);

        // Initialize FloatingActionButton
        fabAddAppointment = findViewById(R.id.fab_add_appointment);
        fabAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to NewAppointmentActivity
                Intent intent = new Intent(AppointmentsActivity.this, NewAppointmentActivity.class);
                startActivity(intent);
            }
        });

        // Fetch data from Firebase
        fetchAppointments();
    }

    private void fetchAppointments() {
        DatabaseAPI.getInstance().getAllAppointments(new CustomCallback<HashMap<String, Appointment>>() {
            @Override
            public void onCallback(HashMap<String, Appointment> data) {
                // Clear the existing list
                appointmentList.clear();
                // Iterate through the HashMap and add the values to the list
                for (Appointment appointm : data.values()) {
                    appointmentList.add(appointm);
                }
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }


        });
    }
}