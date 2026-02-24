package com.example.tentoclock.pania.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tentoclock.R;
import com.example.tentoclock.class_models.Pani;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.general.Utils;
import com.example.tentoclock.interfaces.CustomCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPaniFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPaniFragment extends Fragment {
    private LinearLayout colorList;
    private ImageButton addColorBtn;
    private TextView colorNameToAdd;
    private AutoCompleteTextView pamphletPani;
    private EditText codePani;
    private EditText namePani;
    private AutoCompleteTextView seriesPani;
    private EditText pricePani;
    private EditText descPani;
    private Button addPaniBtn;
    private ArrayList<String> addedColors;
    private DatabaseAPI databaseAPI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_pani, container, false);

        pamphletPani = view.findViewById(R.id.pamphletPani);
        codePani = view.findViewById(R.id.codePani);
        namePani = view.findViewById(R.id.namePani);
        seriesPani = view.findViewById(R.id.seriesPani);
        pricePani = view.findViewById(R.id.pricePani);
        descPani = view.findViewById(R.id.descPani);

        addPaniBtn = view.findViewById(R.id.addPaniBtn);
        addedColors = new ArrayList<>();
        colorList = view.findViewById(R.id.colorList);
        colorNameToAdd = view.findViewById(R.id.colorsPani);
        addColorBtn = view.findViewById(R.id.addColorBtn);

        databaseAPI = DatabaseAPI.getInstance();


        // Setting the suggestions for the pamphlets
        databaseAPI.getAllPamphlets(new CustomCallback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> pamphlets) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pamphlets);

                pamphletPani.setAdapter(adapter);

                pamphletPani.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        pamphletPani.showDropDown();
                    }
                });
            }
        });
        //TODO: Set the suggestions for the series



        addColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!colorNameToAdd.getText().toString().isEmpty()) {
                    // Creating a color line view with the color_item layout
                    View colorLine = inflater.inflate(R.layout.color_item, container, false);
                    // Setting and storing the color name
                    TextView colorNameText = colorLine.findViewById(R.id.colorName);
                    colorNameText.setText(colorNameToAdd.getText());
                    addedColors.add(colorNameToAdd.getText().toString());
                    colorNameToAdd.setText("");

                    // Handling the delete color buttons
                    ImageButton deleteColor = colorLine.findViewById(R.id.imageButton);
                    deleteColor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addedColors.remove(colorNameText.getText().toString());
                            colorList.removeView(colorLine);
                        }
                    });

                    colorList.addView(colorLine);
                }
            }
        });

        addPaniBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price;
                try {
                    price = Double.parseDouble(pricePani.getText().toString());
                }
                catch (Exception e) {
                    price = 0;
                    e.printStackTrace();
                }

                if(!(Utils.isEditTextBlank(seriesPani) | Utils.isEditTextBlank(namePani) | Utils.isEditTextBlank(codePani) | Utils.isEditTextBlank(pamphletPani))) {
                    // Creating a new Pani object and adding it to the database
                    Pani pani = new Pani(codePani.getText().toString(), namePani.getText().toString(), addedColors, seriesPani.getText().toString(), price, descPani.getText().toString());
                    DatabaseAPI.getInstance().addPani(pani, pamphletPani.getText().toString());

                    // Clearing the fields
                    codePani.setText("");
                    namePani.setText("");
                    pricePani.setText("");
                    descPani.setText("");
                    addedColors.clear();
                    colorList.removeAllViews();
                }
            }
        });


        // Listener to remove keyboard when clicking outside the EditTexts
        View scrollView = view.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener((v, event) -> {
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
}

