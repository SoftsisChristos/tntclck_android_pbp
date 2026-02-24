package com.example.tentoclock;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tentoclock.class_models.Partner;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.general.Utils;
import com.example.tentoclock.interfaces.CustomCallback;

public class Partner_Expanded extends AppCompatActivity {
    private String partnerId;
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
    private Button deleteBtn;
    private Button updateBtn;
    private DatabaseAPI databaseAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_partner_expanded);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Get Bundle from the Intent
        Bundle extras = getIntent().getExtras();
        //Checking if there are data passed in the intent
        if (extras != null) {
            //Retrieve data passed in the Intent
            partnerId = extras.getString("partnerId");
        }

        databaseAPI = DatabaseAPI.getInstance();

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
        deleteBtn = findViewById(R.id.deleteBtn);
        updateBtn = findViewById(R.id.updateBtn);

        databaseAPI.getPartner(partnerId, new CustomCallback<Partner>() {
            @Override
            public void onCallback(Partner partner) {
                firstName.setText(partner.getFirstname());
                lastName.setText(partner.getLastname());
                phone.setText(partner.getPhone());
                landline.setText(partner.getLandline());
                email.setText(partner.getEmail());
                profession.setText(partner.getProfession());
                company.setText(partner.getCompany());
                afm.setText(partner.getAfm());
                address.setText(partner.getAddress());
                regionArea.setText(partner.getRegionArea());
                postcode.setText(partner.getPostcode());
            }
        });

        deleteBtn.setOnClickListener(view -> {
            // Building the dialog
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
            dialogBuilder.setMessage("Είστε σίγουρος ότι θέλετε να διαγράψετε τον συνεργάτη;")
                    .setTitle("Προσοχή!");
            dialogBuilder.setPositiveButton("Επιβεβαίωση", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    databaseAPI.removePartner(partnerId);
                    finish();
                }
            });
            dialogBuilder.setNegativeButton("Ακύρωση", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Do nothing
                }
            });

            // Showing the dialog
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        });

        updateBtn.setOnClickListener(view -> {
            if(!(Utils.isEditTextBlank(postcode) | Utils.isEditTextBlank(regionArea) | Utils.isEditTextBlank(address) | Utils.isEditTextBlank(afm) | Utils.isEditTextBlank(profession) | Utils.isEditTextBlank(email) | Utils.isEditTextBlank(phone) | Utils.isEditTextBlank(lastName) | Utils.isEditTextBlank(firstName))) {
                Partner newPartner = new Partner(firstName.getText().toString(), lastName.getText().toString(), landline.getText().toString(), phone.getText().toString(), email.getText().toString(), address.getText().toString(), regionArea.getText().toString(), postcode.getText().toString(), profession.getText().toString(), company.getText().toString(), afm.getText().toString());
                databaseAPI.updatePartner(partnerId, newPartner);
                finish();
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}