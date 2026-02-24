package com.example.tentoclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private List<String> mList;
    private OnTentoclockItemListener mOnTentoclockItemListener;

    public NestedAdapter(List<String> mList, OnTentoclockItemListener onTentoclockItemListener) {
        this.mList = mList;
        this.mOnTentoclockItemListener = onTentoclockItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position));
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent, false);
        return new NestedViewHolder(view, mOnTentoclockItemListener);
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTv;
        OnTentoclockItemListener onTentoclockItemListener;

        public NestedViewHolder(@NonNull View itemView, OnTentoclockItemListener onTentoclockItemListener) {
            super(itemView);

            mTv = itemView.findViewById(R.id.nestedItemTv);

            this.onTentoclockItemListener = onTentoclockItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTentoclockItemListener.onTentoclockItemClick(getAdapterPosition());
        }
    }

    public interface OnTentoclockItemListener {
        void onTentoclockItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}