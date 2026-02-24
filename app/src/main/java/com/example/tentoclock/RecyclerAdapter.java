package com.example.tentoclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerAdapter extends FirebaseRecyclerAdapter<Customer, RecyclerAdapter.myViewHolder> {

    private OnCustomerItemListener mOnCustomerItemListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<Customer> options, OnCustomerItemListener onCustomerItemListener) {
        super(options);
        this.mOnCustomerItemListener = onCustomerItemListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Customer model) {
        holder.email.setText(model.getEmail());
        holder.firstname.setText(model.getFirstname());
        holder.lastname.setText(model.getLastname());
        holder.regionArea.setText(model.getRegionArea());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        return new myViewHolder(view, mOnCustomerItemListener);
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView email, firstname, lastname, regionArea;
        OnCustomerItemListener onCustomerItemListener;

        public myViewHolder(@NonNull View itemView, OnCustomerItemListener onCustomerItemListener) {
            super(itemView);

            email = itemView.findViewById(R.id.customerCard_email);
            firstname = itemView.findViewById(R.id.customerCard_firstname);
            lastname = itemView.findViewById(R.id.customerCard_lastname);
            regionArea = itemView.findViewById(R.id.customerCard_perioxh);

            this.onCustomerItemListener = onCustomerItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCustomerItemListener.onCustomerItemClick(getAdapterPosition());
        }
    }

    public interface OnCustomerItemListener {
        void onCustomerItemClick(int position);
    }
}
