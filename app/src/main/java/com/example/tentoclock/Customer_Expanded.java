package com.example.tentoclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class Customer_Expanded extends AppCompatActivity {

    DatabaseReference customers_Ref = FirebaseDatabase.getInstance().getReference("customers");
    DatabaseReference customerCards_Ref = FirebaseDatabase.getInstance().getReference("customerCards");
    Button btn_deleteCustomer, btn_updateCustomer;
    String og_firstname, og_lastname, og_email, og_phone, og_address, og_area, og_postcode, og_floor, og_bell;
    String newFirstname, newLastname, newEmail, newPhone, newAddress, newArea, newPostcode, newFloor, newBell;
    TextView tvFirstName, tvLastName, tvEmail, tvPhone, tvAddress, tvArea, tvPostalCode, tvFloor, tvDoorbell;
    Boolean finishOnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_expanded);

        // Παραλαβή των δεδομένων από το Intent
        Intent intent_rcv = getIntent();
        String lastname = intent_rcv.getStringExtra("customer_lastname");
        fetchCustomerByLastname(lastname);

        // Υλοποίηση listener για το button διαγραφή πελάτη
        btn_deleteCustomer = findViewById(R.id.btn_delete);
        btn_deleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Building the dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                dialogBuilder.setMessage("Είστε σίγουρος ότι θέλετε να διαγράψετε τον πελάτη;")
                        .setTitle("Προσοχή!");
                dialogBuilder.setPositiveButton("Επιβεβαίωση", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteCustomer(lastname, true);
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
            }
        });

        // Υλοποίηση listener για το button ενημέρωση πελάτη
        btn_updateCustomer = findViewById(R.id.btn_update);
        btn_updateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFirstname = tvFirstName.getText().toString();
                newLastname = tvLastName.getText().toString();
                newEmail = tvEmail.getText().toString();
                newPhone = tvPhone.getText().toString();
                newAddress = tvAddress.getText().toString();
                newArea = tvArea.getText().toString();
                newPostcode = tvPostalCode.getText().toString();
                newFloor = tvFloor.getText().toString();
                newBell = tvDoorbell.getText().toString();

                updateCustomer(og_firstname, og_lastname, og_email, og_phone, og_address, og_area, og_postcode, og_floor, og_bell,
                        newFirstname, newLastname, newEmail, newPhone, newAddress, newArea, newPostcode, newFloor, newBell);
            }
        });
    }

    private void fetchCustomerByLastname(String lastname) {

        // Query για πελάτη με το συγκεκριμένο επώνυμο (lastname)
        Query query = customers_Ref.orderByChild("lastname").equalTo(lastname);

        // Εκτέλεση του query
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Αν βρεθούν αποτελέσματα
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Λήψη του αντικειμένου Customer
                        Customer customer = snapshot.getValue(Customer.class);

                        // Γέμισμα των TextViews με τα στοιχεία του πελάτη
                        if (customer != null) {
                            fillCustomerDetails(customer);
                        }
                    }
                } else {
                    // Δεν βρέθηκε πελάτης
                    Toast.makeText(getApplicationContext(), "Customer not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Διαχείριση error
                Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillCustomerDetails(Customer customer) {

        // Βρίσκω τα TextViews από το XML
        tvFirstName = findViewById(R.id.txtview_firstname);
        tvLastName = findViewById(R.id.txtview_lastname);
        tvEmail = findViewById(R.id.txtview_email);
        tvPhone = findViewById(R.id.txtview_phone);
        tvAddress = findViewById(R.id.txtview_address);
        tvArea = findViewById(R.id.txtview_area);
        tvPostalCode = findViewById(R.id.txtview_pc);
        tvFloor = findViewById(R.id.txtview_floor);
        tvDoorbell = findViewById(R.id.txtview_bell);

        // Γεμίζω τα TextViews με τα στοιχεία του πελάτη
        tvFirstName.setText(customer.getFirstname());
        tvLastName.setText(customer.getLastname());
        tvEmail.setText(customer.getEmail());
        tvPhone.setText(customer.getPhone());
        tvAddress.setText(customer.getAddress());
        tvArea.setText(customer.getRegionArea());
        tvPostalCode.setText(customer.getPostcode());
        tvFloor.setText(customer.getFloor());
        tvDoorbell.setText(customer.getDoorbell());

        // Παραλαβή των αρχικών δεδομένων του πελάτη
        og_firstname = customer.getFirstname();
        og_lastname = customer.getLastname();
        og_email = customer.getEmail();
        og_phone = customer.getPhone();
        og_address = customer.getAddress();
        og_area = customer.getRegionArea();
        og_postcode = customer.getPostcode();
        og_floor = customer.getFloor();
        og_bell = customer.getDoorbell();
    }

    private void deleteCustomer(String lastname, Boolean finishOrNot) {

        finishOnDelete = finishOrNot;

        // Εκτέλεση διαγραφής του πελατη απο τον πινακα customers
        customers_Ref.child(lastname).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(finishOnDelete)
                        {
                            Toast.makeText(Customer_Expanded.this, "Ο πελάτης διαγράφηκε επιτυχώς!", Toast.LENGTH_SHORT).show();
                            // Προαιρετικά, μπορείς να κλείσεις την οθόνη ή να επιστρέψεις πίσω
                            finish(); // Επιστροφή στο προηγούμενο Activity
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Σφάλμα κατά τη διαγραφή
                        Toast.makeText(Customer_Expanded.this, "Αποτυχία διαγραφής: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Εκτέλεση διαγραφής του πελατη απο τον πινακα customerCards
        customerCards_Ref.child(lastname).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(finishOnDelete)
                            finish(); // Επιστροφή στο προηγούμενο Activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Σφάλμα κατά τη διαγραφή
                        Toast.makeText(Customer_Expanded.this, "Αποτυχία διαγραφής: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateCustomer(String og_firstname, String og_lastname, String og_email, String og_phone,
                                String og_address, String og_area, String og_postcode, String og_floor, String og_bell,
                                String newFirstname, String newLastname, String newEmail, String newPhone,
                                String newAddress, String newArea, String newPostCode, String newFloor, String newBell) {

        // Έλεγχος αν κάποιο από τα πεδία έχει αλλάξει
        boolean isChanged = !og_firstname.equals(newFirstname) || !og_lastname.equals(newLastname) ||
                !og_email.equals(newEmail) || !og_phone.equals(newPhone) ||
                !og_address.equals(newAddress) || !og_area.equals(newArea) ||
                !og_postcode.equals(newPostCode) || !og_floor.equals(newFloor) || !og_bell.equals(newBell);

        if (isChanged) {

            //διαγραφω τον τρεχοντα πελατη
            deleteCustomer(og_lastname, false);

            //ετοιμαζω τα δεδομενα μου με normalizing τα strings
            String newFirstname_normalized, newLastname_normalized, newAddress_normalized, newArea_normalized, newBell_normalized;

            newFirstname_normalized = Normalizer.normalize(newFirstname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            newLastname_normalized = Normalizer.normalize(newLastname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            newAddress_normalized = Normalizer.normalize(newAddress, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            newArea_normalized = Normalizer.normalize(newArea, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            newBell_normalized = Normalizer.normalize(newBell, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

            // Δημιουργούμε ένα map με τα νέα δεδομένα
            Map<String, Object> customerUpdated = new HashMap<>();
            customerUpdated.put("firstname", newFirstname_normalized.toUpperCase());
            customerUpdated.put("lastname", newLastname_normalized.toUpperCase());
            customerUpdated.put("email", newEmail);
            customerUpdated.put("phone", newPhone.toUpperCase());
            customerUpdated.put("address", newAddress_normalized.toUpperCase());
            customerUpdated.put("regionArea", newArea_normalized.toUpperCase());
            customerUpdated.put("postcode", newPostCode.toUpperCase());
            customerUpdated.put("floor", newFloor.toUpperCase());
            customerUpdated.put("doorbell", newBell_normalized.toUpperCase());

            //γραφω τον πελατη στην firebase στον πινακα "customerCards"
            customerCards_Ref.child(newLastname_normalized.toUpperCase()).setValue(customerUpdated);
            //γραφω τον πελατη στην firebase στον πινακα "customers"
            customers_Ref.child(newLastname_normalized.toUpperCase()).setValue(customerUpdated).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Customer_Expanded.this, "Τα στοιχεία ενημερώθηκαν επιτυχώς!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        } else {
            Toast.makeText(Customer_Expanded.this, "Δεν υπάρχουν αλλαγές για αποθήκευση.", Toast.LENGTH_SHORT).show();
        }

    }
}