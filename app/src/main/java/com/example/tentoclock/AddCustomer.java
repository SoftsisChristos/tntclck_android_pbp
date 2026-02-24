package com.example.tentoclock;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class AddCustomer extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyFirstAdapterLOL adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        tabLayout.addTab(tabLayout.newTab().setText("  Στοιχεία Βήμα-1  "));
        tabLayout.addTab(tabLayout.newTab().setText("  Στοιχεία Βήμα-2  "));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MyFirstAdapterLOL(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

//                if(tab.getPosition() == 0)
//                {
//                    ProgressBar progressBar = findViewById(R.id.progressBar);
//
//                    // Χρησιμοποίησε τον ObjectAnimator για να κάνεις animate το progress
//                    ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 50, 100);
//                    animation.setDuration(1500); // 2 δευτερόλεπτα διάρκεια animation
//                    animation.start();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}

