package com.example.tentoclock.pania.fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SearchView;

import com.example.tentoclock.AddPartner;
import com.example.tentoclock.Partners;
import com.example.tentoclock.R;
import com.example.tentoclock.class_models.Pani;
import com.example.tentoclock.class_models.Partner;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.interfaces.CustomCallback;
import com.example.tentoclock.recycler_adapters.RecyclerAdapterPania;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaniaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaniaFragment extends Fragment {
    private DatabaseAPI databaseAPI;
    private ArrayList<String> allPamphlets;
    private Spinner pamphletSpinner;
    private Context context;
    private String selectedPamphlet;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterPania adapter;
    private HashMap<String, Pani> paniaFromPamphlet;
    private FloatingActionButton addButton;
    private SearchView searchView;

    PaniListener activityCallback;
    public interface PaniListener {
        void onPaniClick(String pamphlet, Pani pani);
        void onAddClick();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (PaniListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PaniListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pania, container, false);

        context = getContext();


        // Getting database instance
        databaseAPI = DatabaseAPI.getInstance();


        pamphletSpinner = view.findViewById(R.id.pamphletSpinner);
        recyclerView = view.findViewById(R.id.paniaRecycler);
        layoutManager = new LinearLayoutManager(context);

        addButton = view.findViewById(R.id.addNewPani);
        searchView = view.findViewById(R.id.paniaSearchview);;


        //Setting an adapter to the recycleView
        allPamphlets = new ArrayList<>(); // Setting them initially so that they arent null when the activity is resumed
        paniaFromPamphlet = new HashMap<>();
        //selectedPamphlet = "";
        adapter = new RecyclerAdapterPania(paniaFromPamphlet, activityCallback, selectedPamphlet);
        databaseAPI.getAllPamphlets(new CustomCallback<ArrayList<String>> () {
            @Override
            public void onCallback(ArrayList<String> pamphlets) {
                // Populating the spinner with pamphlets
                pamphlets.remove("Nyfan");
                populatePamphletSpinner(pamphlets);
                // Getting the currently selected pamphlet
                selectedPamphlet = pamphletSpinner.getSelectedItem().toString();

                // Storing all the pamphlets
                allPamphlets.addAll(pamphlets);

                // Populating the recycler with pania
                populatePaniaRecycler();
            }
        });

        pamphletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Getting the currently selected pamphlet
                selectedPamphlet = parentView.getItemAtPosition(position).toString();

                // Populating the recycler with pania
                populatePaniaRecycler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        // Handling search queries
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // Filters pania
                HashMap<String, Pani> filteredPania = databaseAPI.searchPania(queryText, paniaFromPamphlet);
                // Sets them to the adapter
                adapter.setAllPania(filteredPania);
                // Refresh adapter
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                // Filters pania
                HashMap<String, Pani> filteredPania = databaseAPI.searchPania(queryText, paniaFromPamphlet);
                // Sets them to the adapter
                adapter.setAllPania(filteredPania);
                // Refresh adapter
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // Handling add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Informing the activity
                activityCallback.onAddClick();
            }
        });



        // Listener to remove keyboard when clicking outside the EditTexts
        View rootView = view.findViewById(R.id.rootLayout);
        rootView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View focusedView = getActivity().getCurrentFocus();
                if (focusedView instanceof EditText) {
                    focusedView.clearFocus();
                    // Hide the keyboard as well
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        View paniaRecycler = view.findViewById(R.id.paniaRecycler);
        paniaRecycler.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View focusedView = getActivity().getCurrentFocus();
                if (focusedView instanceof EditText) {
                    focusedView.clearFocus();
                    // Hide the keyboard as well
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                }
            }
            // Call performClick for accessibility
            v.performClick();
            return false;
        });


        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        // When returning from adding/editing a pani, the adapter may need to be refreshed
        if(adapter != null && selectedPamphlet != null)
        {
            databaseAPI.getAllPaniaFromPamphlet(selectedPamphlet, new CustomCallback<HashMap<String, Pani>>(){
                @Override
                public void onCallback(HashMap<String, Pani> allPania) {
                    // Storing them
                    paniaFromPamphlet = allPania;
                    // Updating the adapter
                    adapter.setAllPania(allPania);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    public void populatePamphletSpinner(ArrayList<String> allPamphlets){
        pamphletSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, allPamphlets));
    }

    public void populatePaniaRecycler(){
        //Setting a layout manager to the recyclerView
        recyclerView.setLayoutManager(layoutManager);
        //Setting an adapter to the recycleView
        databaseAPI.getAllPaniaFromPamphlet(selectedPamphlet, new CustomCallback<HashMap<String, Pani>>(){
            @Override
            public void onCallback(HashMap<String, Pani> allPania) {
                // Getting all pania from the database for the selected pamphlet
                paniaFromPamphlet = allPania;
                // Setting them to the adapter
                adapter = new RecyclerAdapterPania(allPania, activityCallback, selectedPamphlet);
                recyclerView.setAdapter(adapter);
                // Enabling search and add button
                addButton.setEnabled(true);
                searchView.setEnabled(true);
                // Submits the current query again because the pamphlet may have changed
                searchView.setQuery(searchView.getQuery(), true);
            }
        });


    }
}