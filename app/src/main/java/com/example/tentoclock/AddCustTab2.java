package com.example.tentoclock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Normalizer;
import java.util.HashMap;

public class AddCustTab2 extends Fragment {

    View view;

    EditText editText_perioxh, editText_tk, editText_orofos, editText_koydouni;

    String tmpSaved_onoma2, tmpSaved_eponymo2, tmpSaved_kinhto2, tmpSaved_email2, tmpSaved_odos2;

    String tmpSaved_perioxh, tmpSaved_tk, tmpSaved_orofos, tmpSaved_koydouni;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_cust_tab2, container, false);

        pareTaSimpliromenaPediaPelathApoToAlloTab();

        Button btn_prosthikiPelath = view.findViewById(R.id.btn_prosthikiPelath);
        btn_prosthikiPelath.setEnabled(true);
        btn_prosthikiPelath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText_perioxh = view.findViewById(R.id.editText_perioxh);
                tmpSaved_perioxh = editText_perioxh.getText().toString();

                editText_tk = view.findViewById(R.id.editText_tk);
                tmpSaved_tk = editText_tk.getText().toString();

                editText_orofos = view.findViewById(R.id.editText_orofos);
                tmpSaved_orofos = editText_orofos.getText().toString();

                editText_koydouni = view.findViewById(R.id.editText_koydouni);
                tmpSaved_koydouni = editText_koydouni.getText().toString();

                if (!tmpSaved_onoma2.isEmpty() && !tmpSaved_eponymo2.isEmpty() && !tmpSaved_kinhto2.isEmpty() && !tmpSaved_email2.isEmpty() && !tmpSaved_odos2.isEmpty() && !tmpSaved_perioxh.isEmpty() && !tmpSaved_tk.isEmpty() && !tmpSaved_orofos.isEmpty() && !tmpSaved_koydouni.isEmpty()) {
                    addCustomerToDB(tmpSaved_onoma2, tmpSaved_eponymo2, tmpSaved_kinhto2, tmpSaved_email2, tmpSaved_odos2, tmpSaved_perioxh, tmpSaved_tk, tmpSaved_orofos, tmpSaved_koydouni);
                    btn_prosthikiPelath.setEnabled(false);
                    btn_prosthikiPelath.setBackgroundColor(Color.parseColor("#BDBDBD"));
                    btn_prosthikiPelath.setTextColor(Color.parseColor("#FFFFFF"));

                    // Ξεκίνησε το Activity που εμφανίζει τους πελάτες
                    Intent intent = new Intent(getActivity(), Customers.class);
                    startActivity(intent);

                    // Κλείσε το τρέχον Activity
                    getActivity().finish();
                } else if (!tmpSaved_tk.matches("[0-9]+") && !tmpSaved_tk.isEmpty())
                    Toast.makeText(getActivity(), "Ο ταχυδρομικός κώδικας δεν είναι έγκυρος", Toast.LENGTH_SHORT).show();
                else if (!tmpSaved_kinhto2.matches("[0-9]+") && !tmpSaved_kinhto2.isEmpty())
                    Toast.makeText(getActivity(), "Ο αριθμός κινητού δεν είναι έγκυρος", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(), "Συμπλήρωσε Όλα Τα Πεδία", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void addCustomerToDB(String tmpSaved_onoma2, String tmpSaved_eponymo2, String tmpSaved_kinhto2, String tmpSaved_email2, String tmpSaved_odos2, String tmpSaved_perioxh, String tmpSaved_tk, String tmpSaved_orofos, String tmpSaved_koydouni) {

        //normalizing my strings
        String onoma_normalized, eponymo_normalized, odos_normalized, perioxh_normalized, koudouni_normalized;
        onoma_normalized = Normalizer.normalize(tmpSaved_onoma2, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        eponymo_normalized = Normalizer.normalize(tmpSaved_eponymo2, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        odos_normalized = Normalizer.normalize(tmpSaved_odos2, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        perioxh_normalized = Normalizer.normalize(tmpSaved_perioxh, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        koudouni_normalized = Normalizer.normalize(tmpSaved_koydouni, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        //creating the customer object
        Customer customer = new Customer(onoma_normalized.toUpperCase(), eponymo_normalized.toUpperCase(), tmpSaved_kinhto2, tmpSaved_email2, odos_normalized.toUpperCase(),
                perioxh_normalized.toUpperCase(), tmpSaved_tk, tmpSaved_orofos, koudouni_normalized.toUpperCase());
        //creating the customerCard object
        Customer customerCard = new Customer(tmpSaved_email2, onoma_normalized.toUpperCase(), eponymo_normalized.toUpperCase(), perioxh_normalized.toUpperCase());

        //instantiating database connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference customersRef = database.getReference("customers");
        DatabaseReference customerCardsRef = database.getReference("customerCards");

        //writing the customer to the DB "customers"
        customersRef.child(eponymo_normalized.toUpperCase()).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Customer Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        //writing the customer to the DB "customerCards"
        customerCardsRef.child(eponymo_normalized.toUpperCase()).setValue(customerCard).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void pareTaSimpliromenaPediaPelathApoToAlloTab() {

        //εδω παιρνω απο το tab1 ολα τα στοιχεια απο τα πεδια που εχει πληκτρολογησει ο χρηστης
        getParentFragmentManager().setFragmentResultListener("dataFrom_onoma", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle rs_onoma) {
                tmpSaved_onoma2 = rs_onoma.getString("df_onoma");
            }
        });
        getParentFragmentManager().setFragmentResultListener("dataFrom_eponymo", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle rs_eponymo) {
                tmpSaved_eponymo2 = rs_eponymo.getString("df_eponymo");
            }
        });
        getParentFragmentManager().setFragmentResultListener("dataFrom_kinhto", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle rs_kinhto) {
                tmpSaved_kinhto2 = rs_kinhto.getString("df_kinhto");
            }
        });
        getParentFragmentManager().setFragmentResultListener("dataFrom_email", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle rs_email) {
                tmpSaved_email2 = rs_email.getString("df_email");
            }
        });
        getParentFragmentManager().setFragmentResultListener("dataFrom_odos", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle rs_odos) {
                tmpSaved_odos2 = rs_odos.getString("df_odos");
            }
        });
    }

}