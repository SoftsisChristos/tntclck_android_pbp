package com.example.tentoclock.recycler_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tentoclock.R;
import com.example.tentoclock.class_models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<Appointment> appointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_item, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment currentItem = appointmentList.get(position);
        holder.clientTextView.setText(currentItem.getClientId());
        holder.dateTextView.setText(currentItem.getDate());
        holder.timeTextView.setText(currentItem.getTime());
        holder.typeOfWorkTextView.setText(currentItem.getTypeOfWork());
        holder.statusTextView.setText(currentItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView clientTextView;
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView typeOfWorkTextView;
        public TextView statusTextView;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            clientTextView = itemView.findViewById(R.id.appointment_client_text_view);
            dateTextView = itemView.findViewById(R.id.appointment_date_text_view);
            timeTextView = itemView.findViewById(R.id.appointment_time_text_view);
            typeOfWorkTextView = itemView.findViewById(R.id.appointment_type_of_work_text_view);
            statusTextView = itemView.findViewById(R.id.appointment_status_text_view);
        }
    }
}