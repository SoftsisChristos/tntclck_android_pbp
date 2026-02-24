package com.example.tentoclock.recycler_adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.R;
import com.example.tentoclock.class_models.MetrishActive;

import java.util.List;

public class MeasurementItemAdapter extends RecyclerView.Adapter<MeasurementItemAdapter.MeasurementItemViewHolder> {

    public interface ItemActionCallback {
        void onEdit(int position);
        void onDelete(int position);
    }

    private List<MetrishActive> measurementItems;
    private ItemActionCallback callback;

    public MeasurementItemAdapter(List<MetrishActive> measurementItems, ItemActionCallback cb) {
        this.measurementItems = measurementItems;
        this.callback = cb;
    }

    @NonNull
    @Override
    public MeasurementItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measurement_item, parent, false);
        return new MeasurementItemViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MeasurementItemViewHolder holder, int position) {

        MetrishActive currentItem = measurementItems.get(position);
        holder.customerNameTextView.setText(currentItem.getEpitheto());
        holder.customerRegionTextView.setText(currentItem.getPerioxh());
        holder.jobTypeTextView.setText(currentItem.getId_tentasMeVraxiona() + ". " + currentItem.getJobType());
        holder.summaryTextView.setText(currentItem.getSummaryText());

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            // Toggle the visibility of the summary text
            if (holder.summaryTextView.getVisibility() == View.GONE) {
                holder.summaryTextView.setVisibility(View.VISIBLE);
            } else {
                holder.summaryTextView.setVisibility(View.GONE);
            }
        });
        // Long-press listener
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            menu.getMenuInflater().inflate(R.menu.measurement_item_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_edit) {
                    callback.onEdit(position);
                    return true;
                } else if (id == R.id.action_delete) {
                    callback.onDelete(position);
                    return true;
                }
                return false;
            });
            menu.show();
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return measurementItems.size();
    }

    public static class MeasurementItemViewHolder extends RecyclerView.ViewHolder {
        public TextView customerNameTextView;
        public TextView customerRegionTextView;
        public TextView jobTypeTextView;
        public TextView summaryTextView;

        public MeasurementItemViewHolder(View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customer_name_text_view);
            customerRegionTextView = itemView.findViewById(R.id.customer_region_text_view);
            jobTypeTextView = itemView.findViewById(R.id.job_type_text_view);
            summaryTextView = itemView.findViewById(R.id.summary_text_view);
        }
    }
}