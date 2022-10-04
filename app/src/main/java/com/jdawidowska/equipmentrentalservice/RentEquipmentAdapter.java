package com.jdawidowska.equipmentrentalservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.InventoryAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import java.util.ArrayList;

public class RentEquipmentAdapter extends RecyclerView.Adapter<RentEquipmentAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<Inventory> inventoryArrayList;
    public OnItemClickListener onItemClickListener;;

    public RentEquipmentAdapter(Context context, ArrayList<Inventory> inventoryArrayList){
        this.inflater = LayoutInflater.from(context);
        this.inventoryArrayList = inventoryArrayList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onRentBtnClicked(int position);
    }

    // setter to interface to make button clickable
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        Button btnRent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtRentEquipmentRow);
            btnRent = itemView.findViewById(R.id.btnRentEquipmentRow);

            btnRent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onRentBtnClicked(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_rent_equpipment_row, parent, false);
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
