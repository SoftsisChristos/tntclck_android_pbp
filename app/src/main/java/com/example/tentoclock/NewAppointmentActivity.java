package com.example.tentoclock;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tentoclock.class_models.Appointment;
import com.example.tentoclock.db_management.DatabaseAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class NewAppointmentActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TimePicker timePicker;
    private EditText clientEditText;
    private EditText typeOfWorkEditText;
    private EditText notesEditText;
    private Button saveButton;
    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        // Initialize views
        calendarView = findViewById(R.id.calendar_view);
        timePicker = findViewById(R.id.time_picker);
        clientEditText = findViewById(R.id.edit_text_client);
        typeOfWorkEditText = findViewById(R.id.edit_text_type_of_work);
        notesEditText = findViewById(R.id.edit_text_notes);
        saveButton = findViewById(R.id.btn_save_appointment);

        // Set Monday as the first day of the week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        // Set up CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            selectedDate = dateFormat.format(calendar.getTime());
        });

        // Set up TimePicker
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
        });

        // Set up Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppointment();
            }
        });
    }

    private void saveAppointment() {
        // Get data from views
        String clientId = clientEditText.getText().toString().trim();
        String typeOfWork = typeOfWorkEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        // Validate data
        if (selectedDate == null || selectedTime == null || clientId.isEmpty() || typeOfWork.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Appointment object
        String appointmentId = UUID.randomUUID().toString();
        Appointment appointment = new Appointment(appointmentId, clientId, selectedDate, selectedTime, typeOfWork, notes, "Scheduled");

        DatabaseAPI.getInstance().addAppointment(appointment);
    }
}