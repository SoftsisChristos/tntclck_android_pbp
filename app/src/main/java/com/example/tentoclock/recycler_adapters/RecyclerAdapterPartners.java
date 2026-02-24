package com.example.tentoclock.recycler_adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.Partner_Expanded;
import com.example.tentoclock.R;
import com.example.tentoclock.class_models.Partner;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapterPartners extends RecyclerView.Adapter<RecyclerAdapterPartners.PartnerViewHolder>{
    private HashMap<String, Partner> allPartners;
    private ArrayList<String> keySet;

    public RecyclerAdapterPartners(HashMap<String, Partner> allPartners) {
        setAllPartners(allPartners);
    }

    //Holds the lists to be displayed
    public class PartnerViewHolder extends RecyclerView.ViewHolder {
        TextView lastName;
        TextView firstName;
        TextView email;
        TextView phone;

        public PartnerViewHolder(View view) {
            super(view);

            lastName = view.findViewById(R.id.partnerCard_lastname);
            firstName = view.findViewById(R.id.partnerCard_firstname);
            email = view.findViewById(R.id.partnerCard_email);
            phone = view.findViewById(R.id.partnerCard_phone);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Getting the partner's ID from the keySet
                    String partnerId = keySet.get(getAbsoluteAdapterPosition());
                    // Creating a new intent
                    Intent intent = new Intent(v.getContext(), Partner_Expanded.class);
                    // Passing the ID to the new activity
                    intent.putExtra("partnerId", partnerId);
                    // Ask Android to start the new Activity
                    v.getContext().startActivity(intent);
                }
            });
        }
    }


    @NonNull
    @Override
    public RecyclerAdapterPartners.PartnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partner_item, parent, false);
        return new PartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPartners.PartnerViewHolder holder, int position) {
        keySet = new ArrayList<>(getAllPartners().keySet());
        Partner partner = getPartner(keySet.get(position));
        if(partner != null)
        {
            holder.lastName.setText(partner.getLastname());
            holder.firstName.setText(partner.getFirstname());
            holder.email.setText(partner.getEmail());
            holder.phone.setText(partner.getPhone());
        }
        else
        {
            Log.e("PARTNERS_LIST", "Sth went wrong with binding the partners");
        }
    }

    @Override
    public int getItemCount() {
        return getAllPartners().size();
    }

    public HashMap<String, Partner> getAllPartners() {
        return allPartners;
    }

    public void setAllPartners(HashMap<String, Partner> allPartners) {
        this.allPartners = allPartners;
    }

    public Partner getPartner(String partnerId) {
        return allPartners.get(partnerId);
    }
}
