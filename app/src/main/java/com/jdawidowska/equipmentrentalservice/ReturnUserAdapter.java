package com.jdawidowska.equipmentrentalservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.model.RentedInventoryResponse;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ReturnUserAdapter extends RecyclerView.Adapter<ReturnUserAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<RentedInventoryResponse> rentedInventoryResponseList;

    public ReturnUserAdapter(Context context, ArrayList<RentedInventoryResponse> rentedInventoryResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.rentedInventoryResponseList = rentedInventoryResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_user_return_row,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.item.setText(rentedInventoryResponseList.get(position).getEquipment());
        holder.amount.setText(rentedInventoryResponseList.get(position).getAmount().toString());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item, amount;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtItemRetunRow);
            amount = itemView.findViewById(R.id.txtAmountReturnRow);
            button = itemView.findViewById(R.id.btnReturnUserRow);
        }
    }
    @Override
    public int getItemCount() {
        return rentedInventoryResponseList.size();
    }


}
