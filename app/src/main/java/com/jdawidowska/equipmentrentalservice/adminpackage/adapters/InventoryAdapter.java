package com.jdawidowska.equipmentrentalservice.adminpackage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.jdawidowska.equipmentrentalservice.InventoryOnClickInterface;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import org.json.JSONArray;

import java.util.ArrayList;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<Inventory> inventoryList;
    // interface to make button clickable
    public OnItemClickListener onItemClickListener;


    public InventoryAdapter(Context context, ArrayList<Inventory> inventoryList) {
        this.inflater = LayoutInflater.from(context);
        this.inventoryList = inventoryList;
    }
    // interface to make button clickable
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onRemoveBtnClicked(int position);
    }

    // setter to interface to make button clickable
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item, available, total;
        Button btnRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtnventoryRowItemName);
            available =itemView.findViewById(R.id.txtnventoryRowAvaiableAmount);
            total = itemView.findViewById(R.id.txtnventoryRowTotalAmount);
            //finding the right button and set position
            btnRemove = itemView.findViewById(R.id.btnRemoveInventoryRow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onRemoveBtnClicked(position);
                        }
                    }
                }
            });

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
