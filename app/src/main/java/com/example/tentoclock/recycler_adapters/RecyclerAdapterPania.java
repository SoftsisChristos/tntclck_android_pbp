package com.example.tentoclock.recycler_adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.Partner_Expanded;
import com.example.tentoclock.R;
import com.example.tentoclock.class_models.Pani;
import com.example.tentoclock.class_models.Partner;
import com.example.tentoclock.pania.fragments.PaniaFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapterPania extends RecyclerView.Adapter<RecyclerAdapterPania.PaniViewHolder>{
    private HashMap<String, Pani> allPania;
    private ArrayList<String> keySet;
    PaniaFragment.PaniListener activityCallback;
    String selectedPamphlet;

    public RecyclerAdapterPania(HashMap<String, Pani> allPania, PaniaFragment.PaniListener activityCallback, String selectedPamphlet) {
        setAllPania(allPania);
        this.activityCallback = activityCallback;
        this.selectedPamphlet = selectedPamphlet;
    }

    //Holds the lists to be displayed
    public class PaniViewHolder extends RecyclerView.ViewHolder {
        TextView code;
        TextView name;
        TextView series;
        ChipGroup colors;

        public PaniViewHolder(View view) {
            super(view);

            code = view.findViewById(R.id.codePani);
            name = view.findViewById(R.id.namePani);
            series = view.findViewById(R.id.seriesPani);
            colors = view.findViewById(R.id.colorsPani);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Getting the pani's code from the keySet
                    Pani pani = allPania.get(keySet.get(getAbsoluteAdapterPosition()));
                    // Sending the pressed pani's code to the activity
                    activityCallback.onPaniClick(selectedPamphlet, pani);
                }
            });
        }
    }


    @NonNull
    @Override
    public RecyclerAdapterPania.PaniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pani_item, parent, false);
        return new PaniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPania.PaniViewHolder holder, int position) {
        keySet = new ArrayList<>(getAllPania().keySet());
        Pani pani = getPani(keySet.get(position));
        if(pani != null)
        {
            holder.code.setText(pani.getCode());
            holder.name.setText(pani.getName());
            holder.series.setText(pani.getSeries());

            holder.colors.removeAllViews();

            for (String color : pani.getColors()) {
                Chip chip = new Chip(holder.colors.getContext());
                chip.setText(color);
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setFocusable(false);
                holder.colors.addView(chip);
            }
        }
        else
        {
            Log.e("PANIA_LIST", "Sth went wrong with binding the pania");
        }
    }

    @Override
    public int getItemCount() {
        return getAllPania().size();
    }

    public HashMap<String, Pani> getAllPania() {
        return allPania;
    }

    public void setAllPania(HashMap<String, Pani> allPania) {
        this.allPania = allPania;
    }

    public Pani getPani(String paniCode) {
        return allPania.get(paniCode);
    }

}
