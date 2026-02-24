package com.example.tentoclock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFirstAdapterLOL extends FragmentStateAdapter {

    public MyFirstAdapterLOL(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new AddCustTab1();
        else
            return new AddCustTab2();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
