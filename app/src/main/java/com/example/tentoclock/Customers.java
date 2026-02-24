package com.example.tentoclock;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Customers extends AppCompatActivity implements RecyclerAdapter.OnCustomerItemListener {

    private FloatingActionButton floatBtn_addNewCustomer;
    RecyclerView rv_customers;
    RecyclerAdapter recyclerAdapter;

    FirebaseDatabase TentDB;
    DatabaseReference customerCards_reference;

    FirebaseRecyclerOptions<Customer> options;

    SearchView searchView;

    List<Customer> customersList = new ArrayList<>();
    List<Customer> customersFilteredList = new ArrayList<>();

    String newText_forPositionPurposes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        floatBtn_addNewCustomer = findViewById(R.id.floatBtn_addNewCustomer);

        // Καθυστέρηση για 3 δευτερόλεπτα πριν ενεργοποιηθεί το κουμπί
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ενεργοποίηση του κουμπιού μετά από 3 δευτερόλεπτα
                floatBtn_addNewCustomer.setEnabled(true);
            }
        }, 3000); // 3000 χιλιοστά του δευτερολέπτου (3 δευτερόλεπτα)


        TentDB = FirebaseDatabase.getInstance();
        customerCards_reference = TentDB.getReference().child("customerCards");
        populateRecyclerView();


        floatBtn_addNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customers.this, AddCustomer.class);
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.searchview_customers);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    newText_forPositionPurposes = "";
                    populateRecyclerView();
                }
                else
                {
                    newText_forPositionPurposes = newText;
                    filterRecyclerView(newText);
                }

                return true;
            }
        });
    }

    private void populateRecyclerView() {

        rv_customers = findViewById(R.id.recyclerView_customers);
        rv_customers.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<Customer>()
                .setQuery(customerCards_reference, Customer.class)
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("customerCards").orderByChild("lastname").startAt("Π").endAt("Π\uf8ff"), Customer.class)
                .build();
        recyclerAdapter = new RecyclerAdapter(options, this);
        recyclerAdapter.startListening();
        rv_customers.setAdapter(recyclerAdapter);

        customerCards_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customersList.clear(); // Καθαρίζουμε τη λίστα πριν τη γεμίσουμε ξανά

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Customer customer = snapshot.getValue(Customer.class);
                    customersList.add(customer); // Προσθήκη κάθε πελάτη στη λίστα
                }

                // Τυπώνουμε τα περιεχόμενα της λίστας
                for (Customer customer : customersList) {
                    Log.d("CustomerList", customer.getLastname() + " - " + customer.getFirstname() + " - " + customer.getRegionArea());
                }

                // Χειρισμός της γεμάτης customersList
                // π.χ. ανανέωση του RecyclerView με την ενημερωμένη λίστα
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FirebaseError", "loadPost:onCancelled", error.toException());
            }
        });

    }

    protected void filterRecyclerView(String newText) {

        rv_customers = findViewById(R.id.recyclerView_customers);
        rv_customers.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<Customer>()
                .setQuery(customerCards_reference.orderByChild("lastname").startAt(newText.toUpperCase()).endAt(/*newText.toUpperCase() + */ "Π\uf8ff"), Customer.class)
                .build();
        Log.d("FirebaseQuery", "Lastname starting at: " + newText.toUpperCase());

        recyclerAdapter = new RecyclerAdapter(options, this);
        recyclerAdapter.startListening();
        rv_customers.setAdapter(recyclerAdapter);

        Query query = customerCards_reference.orderByChild("lastname").startAt(newText.toUpperCase()).endAt(/*newText.toUpperCase() + */ "Π\uf8ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customersFilteredList.clear(); // Καθαρίζουμε τη λίστα πριν τη γεμίσουμε ξανά

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Customer customer = snapshot.getValue(Customer.class);
                    customersFilteredList.add(customer); // Προσθήκη κάθε πελάτη στη λίστα
                }

                // Τυπώνουμε τα περιεχόμενα της λίστας
                for (Customer customer : customersFilteredList) {
                    Log.d("CustomerList", customer.getLastname() + " - " + customer.getFirstname());
                }

                // Χειρισμός της γεμάτης customersList
                // π.χ. ανανέωση του RecyclerView με την ενημερωμένη λίστα
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FirebaseError", "loadPost:onCancelled", error.toException());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    public void onCustomerItemClick(int position) {

        Log.d(TAG, "onCustomerItemClick: " + position);
        Customer clickedCustomer;

        if (newText_forPositionPurposes.isEmpty()) {
            clickedCustomer = customersList.get(position);
        } else {
            clickedCustomer = customersFilteredList.get(position);
        }

        if (position != RecyclerView.NO_POSITION) {
            // Εδώ μπορείς να ορίσεις τι συμβαίνει όταν πατηθεί το item
            // πχ. Πάσαρε τα δεδομένα του πελάτη στο νέο Activity

            Intent intent_snd = new Intent(this, Customer_Expanded.class);

            intent_snd.putExtra("customer_lastname", clickedCustomer.getLastname());

            startActivity(intent_snd);
        }


    }
}