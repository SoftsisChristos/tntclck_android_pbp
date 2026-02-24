package com.example.tentoclock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.class_models.Partner;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.interfaces.CustomCallback;
import com.example.tentoclock.recycler_adapters.RecyclerAdapterPartners;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

public class Partners extends AppCompatActivity {
    DatabaseAPI databaseAPI;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterPartners adapter;
    SearchView searchView;
    FloatingActionButton addButton;
    HashMap<String, Partner> allPartners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_partners);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchView = findViewById(R.id.searchview_partners);
        addButton = findViewById(R.id.floatBtn_addNewPartner);

        // Getting database instance
        databaseAPI = DatabaseAPI.getInstance();

        recyclerView = findViewById(R.id.recyclerView_partners);
        //Setting a layout manager to the recycleView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //Setting an adapter to the recycleView
        allPartners = new HashMap<>(); // Setting them initially so that they arent null when the activity is resumed
        adapter = new RecyclerAdapterPartners(allPartners);
        databaseAPI.getAllPartners(new CustomCallback<HashMap<String, Partner>>() {
            @Override
            public void onCallback(HashMap<String, Partner> allPartnersFromDB) {
                // Getting all partners from the database
                allPartners = allPartnersFromDB;
                // Setting them to the adapter
                adapter = new RecyclerAdapterPartners(allPartners);
                recyclerView.setAdapter(adapter);
                // Enabling search and add button
                addButton.setEnabled(true);
                searchView.setEnabled(true);
            }
        });

        // Handling search queries
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // Filters partners
                HashMap<String, Partner> filteredPartners = databaseAPI.searchPartners(queryText, allPartners);
                // Sets them to the adapter
                adapter.setAllPartners(filteredPartners);
                // Refresh adapter
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                // Filters partners
                HashMap<String, Partner> filteredPartners = databaseAPI.searchPartners(queryText, allPartners);
                // Sets them to the adapter
                adapter.setAllPartners(filteredPartners);
                // Refresh adapter
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // Handling add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Partners.this, AddPartner.class);
                startActivity(intent);
            }
        });

        // Listener to remove keyboard when clicking outside the EditText
        View rootView = findViewById(R.id.rootLayout);
        rootView.setOnTouchListener((v, event) -> {
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

        // Listener to remove keyboard when clicking outside the EditTexts
        recyclerView.setOnTouchListener((v, event) -> {
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

    @Override
    protected void onResume() {
        super.onResume();
        // When returning from add_partner/partner_expanded, the adapter may need to be refreshed
        if(adapter != null)
        {
            databaseAPI.getAllPartners(new CustomCallback<HashMap<String, Partner>>() {
                @Override
                public void onCallback(HashMap<String, Partner> allPartnersFromDB) {
                    adapter.setAllPartners(allPartnersFromDB);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}