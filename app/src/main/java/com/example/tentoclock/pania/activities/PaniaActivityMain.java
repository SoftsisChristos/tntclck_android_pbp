package com.example.tentoclock.pania.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tentoclock.R;
import com.example.tentoclock.class_models.Pani;
import com.example.tentoclock.pania.fragments.AddPaniFragment;
import com.example.tentoclock.pania.fragments.EditPaniFragment;
import com.example.tentoclock.pania.fragments.PaniaFragment;

public class PaniaActivityMain extends AppCompatActivity implements PaniaFragment.PaniListener {
    // 0 for phone, 1 for tablet
    private int device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pania_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        FragmentContainerView secondaryFragment = findViewById(R.id.secondaryFragment);
        if(secondaryFragment == null){
            device = 0;
        }
        else{
            device = 1;
        }
    }

    @Override
    public void onPaniClick(String pamphlet, Pani pani) {
        EditPaniFragment editPaniFragment = EditPaniFragment.newInstance(pamphlet, pani);
        FragmentManager ftMan = getSupportFragmentManager();
        FragmentTransaction ftTrans = ftMan.beginTransaction();
        // Checks which device the user is using to replace the right fragment
        if(device == 1) {
            ftTrans.replace(R.id.secondaryFragment, editPaniFragment).addToBackStack("editPaniFragment");
        }
        else {

            ftTrans.replace(R.id.onlyFragment, editPaniFragment).addToBackStack("editPaniFragment");
        }
        ftTrans.commit();
    }

    @Override
    public void onAddClick() {
        AddPaniFragment addPaniFragment = new AddPaniFragment();
        FragmentManager ftMan = getSupportFragmentManager();
        FragmentTransaction ftTrans = ftMan.beginTransaction();
        // Checks which device the user is using to replace the right fragment
        if(device == 1) {
            ftTrans.replace(R.id.secondaryFragment, addPaniFragment).addToBackStack("addPaniFragment");
        }
        else {

            ftTrans.replace(R.id.onlyFragment, addPaniFragment).addToBackStack("addPaniFragment");
        }
        ftTrans.commit();
    }
}