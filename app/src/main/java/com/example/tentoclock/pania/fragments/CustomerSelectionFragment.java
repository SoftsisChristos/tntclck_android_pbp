package com.example.tentoclock.pania.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.Customer;
import com.example.tentoclock.R;
import com.example.tentoclock.recycler_adapters.CustomerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerSelectionFragment extends Fragment implements CustomerAdapter.OnCustomerClickListener {

    private RecyclerView customersRecyclerView;
    private CustomerAdapter customerAdapter;
    private List<Customer> customerList;
    private DatabaseReference customersRef;
    private Customer selectedCustomer;

    public CustomerSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_selection, container, false);

        // Initialize the RecyclerView
        customersRecyclerView = view.findViewById(R.id.customers_recycler_view);
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the customerList
        customerList = new ArrayList<>();

        // Initialize the adapter
        customerAdapter = new CustomerAdapter(customerList, this);
        customersRecyclerView.setAdapter(customerAdapter);

        // Get a reference to the "customers" node
        customersRef = FirebaseDatabase.getInstance().getReference("customers");

        // Fetch customers from the database
        fetchCustomers();

        return view;
    }

    private void fetchCustomers() {
        customersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerList.clear();
                for (DataSnapshot customerSnapshot : dataSnapshot.getChildren()) {
                    Customer customer = customerSnapshot.getValue(Customer.class);
                    customerList.add(customer);
                }
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("CustomerSelectionFragment", "Error fetching customers", databaseError.toException());
            }
        });
    }

    @Override
    public void onCustomerClick(Customer customer) {
        selectedCustomer = customer;
        // Send the selected customer to MetrishPreview
        Bundle result = new Bundle();
        result.putParcelable("selectedCustomer", selectedCustomer);
        getParentFragmentManager().setFragmentResult("customerSelection", result);
        // Go back to the previous fragment
        getParentFragmentManager().popBackStack();
    }
}