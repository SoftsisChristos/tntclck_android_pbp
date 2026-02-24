package com.example.tentoclock;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tentoclock.class_models.Partner;
import com.example.tentoclock.db_management.DatabaseAPI;

import com.example.tentoclock.general.Utils;

public class AddPartner extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText landline;
    private EditText email;
    private EditText profession;
    private EditText company;
    private EditText afm;
    private EditText address;
    private EditText regionArea;
    private EditText postcode;
    private Button addPartnerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_partner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        landline = findViewById(R.id.landline);
        email = findViewById(R.id.email);
        profession = findViewById(R.id.profession);
        company = findViewById(R.id.company);
        afm = findViewById(R.id.afm);
        address = findViewById(R.id.address);
        regionArea = findViewById(R.id.regionArea);
        postcode = findViewById(R.id.postcode);
        addPartnerBtn = findViewById(R.id.addPaniBtn);

        addPartnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Utils.isEditTextBlank(postcode) | Utils.isEditTextBlank(regionArea) | Utils.isEditTextBlank(address) | Utils.isEditTextBlank(afm) | Utils.isEditTextBlank(profession) | Utils.isEditTextBlank(email) | Utils.isEditTextBlank(phone) | Utils.isEditTextBlank(lastName) | Utils.isEditTextBlank(firstName))) {
                    Partner partner = new Partner(firstName.getText().toString(), lastName.getText().toString(), landline.getText().toString(), phone.getText().toString(), email.getText().toString(), address.getText().toString(), regionArea.getText().toString(), postcode.getText().toString(), profession.getText().toString(), company.getText().toString(), afm.getText().toString());
                    DatabaseAPI.getInstance().addPartner(partner);
                    finish();
                }
            }
        });

        // Listener to remove keyboard when clicking outside the EditTexts
        View scrollView = findViewById(R.id.scrollView);
        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View focusedView = getCurrentFocus();
                if (focusedView instanceof EditText) {
                    focusedView.clearFocus();
                    // Hide the keyboard as well
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                }
            }
            // Call performClick for accessibility
            v.performClick();
            return false;
        });
    }
}