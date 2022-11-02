package com.jdawidowska.equipmentrentalservice.activities.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import java.util.List;

public class UserRentingAdapter extends RecyclerView.Adapter<UserRentingAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private final List<Inventory> inventoryArrayList;

    public UserRentingAdapter(Context context, List<Inventory> inventoryArrayList){
        this.inflater = LayoutInflater.from(context);
        this.inventoryArrayList = inventoryArrayList;
    }

    public interface OnItemClickListener{
        void onRentBtnClicked(int position);
    }

    // setter to interface to make button clickable
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item;
        Button btnRent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtRentEquipmentRow);
            btnRent = itemView.findViewById(R.id.btnRentEquipmentRow);

            btnRent.setOnClickListener(view -> {
                if(onItemClickListener != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onItemClickListener.onRentBtnClicked(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_inventory_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText(inventoryArrayList.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return inventoryArrayList.size();
    }
}
