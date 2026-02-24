package com.example.tentoclock.pania.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
 * Use the {@link EditPaniFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPaniFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAMPHLET = "pamphlet";
    private static final String ARG_PANI = "pani";


    private String oldPamphlet;
    private Pani pani;
    private String oldCode;
    private LinearLayout colorList;
    private ImageButton addColorBtn;
    private TextView colorNameToAdd;
    private AutoCompleteTextView pamphletPani;
    private EditText codePani;
    private EditText namePani;
    private AutoCompleteTextView seriesPani;
    private EditText pricePani;
    private EditText descPani;
    private Button deleteBtn;
    private Button updateBtn;
    private ArrayList<String> addedColors;
    private DatabaseAPI databaseAPI;


    public EditPaniFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param oldPamphlet Parameter 1.
     * @param pani Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    public static EditPaniFragment newInstance(String oldPamphlet, Pani pani) {
        EditPaniFragment fragment = new EditPaniFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PAMPHLET, oldPamphlet);
        args.putParcelable(ARG_PANI, pani);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            oldPamphlet = getArguments().getString(ARG_PAMPHLET);
            pani = getArguments().getParcelable(ARG_PANI);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_pani, container, false);

        databaseAPI = DatabaseAPI.getInstance();

        pamphletPani = view.findViewById(R.id.pamphletPani);
        codePani = view.findViewById(R.id.codePani);
        namePani = view.findViewById(R.id.namePani);
        seriesPani = view.findViewById(R.id.seriesPani);
        pricePani = view.findViewById(R.id.pricePani);
        descPani = view.findViewById(R.id.descPani);

        colorList = view.findViewById(R.id.colorList);
        colorNameToAdd = view.findViewById(R.id.colorsPani);
        addColorBtn = view.findViewById(R.id.addColorBtn);

        deleteBtn = view.findViewById(R.id.deleteBtn);
        updateBtn = view.findViewById(R.id.updateBtn);


        pamphletPani.setText(oldPamphlet);
        codePani.setText(pani.getCode());
        oldCode = pani.getCode();
        namePani.setText(pani.getName());
        seriesPani.setText(pani.getSeries());
        pricePani.setText(Double.toString(pani.getPrice()));
        descPani.setText(pani.getDescription());
        addedColors = pani.getColors();



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



        // Populating the color list
        for (String color : addedColors) {
            // Creating a color line view with the color_item layout
            View colorLine = inflater.inflate(R.layout.color_item, container, false);
            // Setting and storing the color name
            TextView colorNameText = colorLine.findViewById(R.id.colorName);
            colorNameText.setText(color);

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


        deleteBtn.setOnClickListener(v -> {
            if(!(Utils.isEditTextBlank(codePani) | Utils.isEditTextBlank(pamphletPani))) {
                // Building the dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                dialogBuilder.setMessage("Είστε σίγουρος ότι θέλετε να διαγράψετε το πανί;")
                        .setTitle("Προσοχή!");
                dialogBuilder.setPositiveButton("Επιβεβαίωση", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseAPI.removePani(codePani.getText().toString(), pamphletPani.getText().toString());
                        // Removing the fragment from the backstack
                        ((AppCompatActivity)getContext()).getSupportFragmentManager().popBackStack("editPaniFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

        updateBtn.setOnClickListener(v -> {
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
                databaseAPI.updatePani(oldCode, oldPamphlet, pamphletPani.getText().toString(), pani);

                oldPamphlet = pamphletPani.getText().toString();
                oldCode = codePani.getText().toString();
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