package com.example.tentoclock;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.tentoclock.class_models.MetrishActive;
import com.example.tentoclock.class_models.MetrishTenta;
import com.example.tentoclock.db_management.DatabaseAPI;
import com.example.tentoclock.interfaces.FragmentCloseListener;
import com.example.tentoclock.pania.fragments.CustomerSelectionFragment;

import java.util.HashMap;
import java.util.Map;

public class MetrishPreview extends Fragment {

    String[] stringArray;
    int[] intArray;
    int[] intArray2;

    private TextView customerFullnameTextView;
    private TextView customerAddressTextView;
    private TextView customerRegionTextView;
    private Customer selectedCustomer;

    TextView tvSummary;
    String summaryText = "";
    Button btn_FinalSubmit;


    public MetrishPreview() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_metrish_preview, container, false);

        // Initialize views
        customerFullnameTextView = view.findViewById(R.id.customer_fullname_text_view);
        customerRegionTextView = view.findViewById(R.id.customer_region_text_view);
        customerAddressTextView = view.findViewById(R.id.customer_address_text_view);
        Button selectCustomerButton = view.findViewById(R.id.select_customer_button);

        // Set up the FragmentResultListener
        getParentFragmentManager().setFragmentResultListener("customerSelection", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Get the selected customer from the bundle
                selectedCustomer = result.getParcelable("selectedCustomer");
                // Update the UI with the customer's information
                updateUI();
            }
        });

        // Set up the button click listener
        selectCustomerButton.setOnClickListener(v -> {
            navigateToCustomerSelectionFragment(); // loads some customers to select from
        });

        tvSummary = view.findViewById(R.id.tv_summary);
        Button btnClose = view.findViewById(R.id.btn_close);
        btn_FinalSubmit = view.findViewById(R.id.button_finalSubmit);

        enableFinalSubmitButton();

        // Κλείσιμο του fragment
        btnClose.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();

            if (requireActivity() instanceof FragmentCloseListener) {
                ((FragmentCloseListener) requireActivity()).onFragmentClosed();
            }

        });

        btn_FinalSubmit.setOnClickListener(view1 -> {

            String troposProsthikis = getArguments().getString("troposProsthikis");

            if (troposProsthikis.equals("update")) {

                long id_tentas_forEdit = (long) getArguments().getInt("id_tentas");

                MetrishTenta editedTenta = dimiourgiaAntikemenou_TentasMeVraxiones(id_tentas_forEdit);

                // Δημιουργούμε ένα Map με τα νέα δεδομένα
                Map<String, Object> updatedValues = new HashMap<>();
                updatedValues.put("aisthitAera", editedTenta.getAisthitAera()); // αντικατάστησε με τα πραγματικά fields σου
                updatedValues.put("aisthitHliou", editedTenta.getAisthitHliou());
                updatedValues.put("antivaro", editedTenta.getAntivaro());
                updatedValues.put("diametrosAksona", editedTenta.getDiametrosAksona());
                updatedValues.put("diploKouzineto", editedTenta.getDiploKouzineto());
                updatedValues.put("eidosVraxiona", editedTenta.getEidosVraxiona());
                updatedValues.put("filadio", editedTenta.getFiladio());
                updatedValues.put("glossa", editedTenta.getGlossa());
                updatedValues.put("houfta", editedTenta.getHoufta());
                updatedValues.put("houftes", editedTenta.getHouftes());
                updatedValues.put("ipsos", editedTenta.getIpsos());
                updatedValues.put("kodikosPaniou", editedTenta.getKodikosPaniou());
                updatedValues.put("lames", editedTenta.getLames());
                updatedValues.put("malakoi", editedTenta.getMalakoi());
                updatedValues.put("manivela", editedTenta.getManivela());
                updatedValues.put("merosVraxiona", editedTenta.getMerosVraxiona());
                updatedValues.put("metatropes", editedTenta.getMetatropes());
                updatedValues.put("mhkos", editedTenta.getMhkos());
                updatedValues.put("mhkosVraxiona", editedTenta.getMhkosVraxiona());
                updatedValues.put("mhxanismos", editedTenta.getMhxanismos());
                updatedValues.put("moter", editedTenta.getMoter());
                updatedValues.put("oneInchKouzineto", editedTenta.getOneInchKouzineto());
                updatedValues.put("panels", editedTenta.getPanels());
                updatedValues.put("smartControl", editedTenta.getSmartControl());
                updatedValues.put("thlekontrols", editedTenta.getThlekontrols());
                updatedValues.put("vaseis", editedTenta.getVaseis());
                updatedValues.put("vaseisMakries", editedTenta.getVaseisMakries());
                updatedValues.put("vaseisToixou", editedTenta.getVaseisToixou());
                updatedValues.put("xromaPaniou", editedTenta.getXromaPaniou());

                DatabaseAPI.getInstance().updateMetrisi(id_tentas_forEdit, updatedValues, getContext());

            } else {
                DatabaseAPI.getInstance().getNextSequentialId(seqId -> {

                    //retrieving the sequential id from a method created in DatabaseAPI.java
                    if (seqId != null) {
                        MetrishTenta metrish = dimiourgiaAntikemenou_TentasMeVraxiones(seqId);
                        DatabaseAPI.getInstance().addMetrish(metrish);
                        MetrishActive metrishActive = new MetrishActive(seqId, selectedCustomer.getLastname(),
                                customerRegionTextView.getText().toString(), "Πέρασμα Τέντα Με Βραχίονες", summaryText);
                        DatabaseAPI.getInstance().addMetrishActive(metrishActive);
                    }
                });
            }


            getActivity().finish();
        });

        return view;
    }

    private void updateUI() {
        if (selectedCustomer != null) {
            customerFullnameTextView.setText(selectedCustomer.getFirstname() + " " + selectedCustomer.getLastname());
            customerRegionTextView.setText(selectedCustomer.getRegionArea());
            customerAddressTextView.setText(selectedCustomer.getAddress());
        }
    }

    private void navigateToCustomerSelectionFragment() {
        CustomerSelectionFragment customerSelectionFragment = new CustomerSelectionFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, customerSelectionFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void enableFinalSubmitButton() {
        // Παίρνουμε τις επιλογές από τα arguments
        if (getArguments() != null) {
            summaryText = getArguments().getString("summary_text");
            tvSummary.setText(summaryText);

            String ableToSubmit = getArguments().getString("ableToSubmit");
            if (ableToSubmit.equals("true"))
                btn_FinalSubmit.setEnabled(true);
            else {
                btn_FinalSubmit.setEnabled(false);
                Toast.makeText(getActivity(), "Κάτι Δεν Έχεις Συμπληρώσει", Toast.LENGTH_LONG).show();
            }

            // Ανάκτηση String Array
            stringArray = getArguments().getStringArray("key_string_array");
            intArray = getArguments().getIntArray("key_int_array");
            intArray2 = getArguments().getIntArray("key_int_array2");

            btn_FinalSubmit.setEnabled(!customerFullnameTextView.getText().toString().equals("Customer Name"));
        } else {
            summaryText = "";
        }

        // Set up the TextWatcher for customerFullnessTextView
        customerFullnameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if the TextView has text (meaning a customer is selected)
                btn_FinalSubmit.setEnabled(!s.equals("Customer Name"));
            }
        });
    }

    private MetrishTenta dimiourgiaAntikemenou_TentasMeVraxiones(Long seqId) {

        return new MetrishTenta(seqId, stringArray[0], stringArray[1],
                stringArray[2], stringArray[3], stringArray[4],
                stringArray[5], stringArray[6], stringArray[7], stringArray[8], stringArray[9],
                stringArray[10], stringArray[11], stringArray[12], stringArray[13], stringArray[14],
                intArray[0], intArray[1], intArray[2], intArray[3], intArray[4], intArray[5], intArray[6],
                intArray2[0], intArray2[1], intArray2[2], intArray2[3], intArray2[4], intArray2[5],
                intArray2[6], intArray2[7], intArray2[8]);
    }
}