package com.jdawidowska.equipmentrentalservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.adminpackage.RentedInventoryResponseAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import java.util.ArrayList;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<Inventory> inventoryList;

    public InventoryAdapter(Context context, ArrayList<Inventory> inventoryList) {
        this.inflater = LayoutInflater.from(context);
        this.inventoryList = inventoryList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item, available, total;
        Button btnRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtnventoryRowItemName);
            available =itemView.findViewById(R.id.txtnventoryRowAvaiableAmount);
            total = itemView.findViewById(R.id.txtnventoryRowTotalAmount);
            btnRemove = itemView.findViewById(R.id.btnRemoveInventoryRow);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_row, parent, false);
        return new InventoryAdapter.MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText(inventoryList.get(position).getItemName());
        holder.total.setText(inventoryList.get(position).getTotalAmount().toString());
        holder.available.setText(inventoryList.get(position).getAvailableAmount().toString());
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }


}
