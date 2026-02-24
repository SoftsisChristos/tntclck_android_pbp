package com.example.tentoclock.recycler_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.Customer;
import com.example.tentoclock.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private OnCustomerClickListener listener;

    public CustomerAdapter(List<Customer> customerList, OnCustomerClickListener listener) {
        this.customerList = customerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_item, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.firstNameTextView.setText(customer.getFirstname());
        holder.lastNameTextView.setText(customer.getLastname());
        holder.emailTextView.setText(customer.getEmail());
        holder.regionTextView.setText(customer.getRegionArea());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCustomerClick(customer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        public ImageView customerImageView;
        public TextView firstNameTextView;
        public TextView lastNameTextView;
        public TextView emailTextView;
        public TextView regionTextView;

        public CustomerViewHolder(View view) {
            super(view);
            customerImageView = view.findViewById(R.id.img_customer);
            firstNameTextView = view.findViewById(R.id.customerCard_firstname);
            lastNameTextView = view.findViewById(R.id.customerCard_lastname);
            emailTextView = view.findViewById(R.id.customerCard_email);
            regionTextView = view.findViewById(R.id.customerCard_perioxh);
        }
    }

    public interface OnCustomerClickListener {
        void onCustomerClick(Customer customer);
    }
}