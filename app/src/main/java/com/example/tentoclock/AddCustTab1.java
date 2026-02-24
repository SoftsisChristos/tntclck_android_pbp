package com.example.tentoclock;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddCustTab1 extends Fragment {

    View view;

    EditText editText_onoma, editText_eponymo, editText_kinhto, editText_email, editText_odos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_cust_tab1, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

        editText_onoma = view.findViewById(R.id.editText_onoma);
        Bundle rs_onoma = new Bundle();
        rs_onoma.putString("df_onoma", editText_onoma.getText().toString());
        getParentFragmentManager().setFragmentResult("dataFrom_onoma", rs_onoma);

        editText_eponymo = view.findViewById(R.id.editText_eponymo);
        Bundle rs_eponymo = new Bundle();
        rs_eponymo.putString("df_eponymo", editText_eponymo.getText().toString());
        getParentFragmentManager().setFragmentResult("dataFrom_eponymo", rs_eponymo);

        editText_kinhto = view.findViewById(R.id.editText_kinhto);
        Bundle rs_kinhto = new Bundle();
        rs_kinhto.putString("df_kinhto", editText_kinhto.getText().toString());
        getParentFragmentManager().setFragmentResult("dataFrom_kinhto", rs_kinhto);

        editText_email = view.findViewById(R.id.editText_email);
        Bundle rs_email = new Bundle();
        rs_email.putString("df_email", editText_email.getText().toString());
        getParentFragmentManager().setFragmentResult("dataFrom_email", rs_email);

        editText_odos = view.findViewById(R.id.editText_odos);
        Bundle rs_odos = new Bundle();
        rs_odos.putString("df_odos", editText_odos.getText().toString());
        getParentFragmentManager().setFragmentResult("dataFrom_odos", rs_odos);
    }

}