package com.jdawidowska.equipmentrentalservice.activities.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.dto.response.RentedInventoryResponse;

import java.util.List;

public class AdminRentedInventoryAdapter extends RecyclerView.Adapter<AdminRentedInventoryAdapter.MyViewHolder> {

    LayoutInflater inflater;
    List<RentedInventoryResponse> rentedInventoryResponseList;

    public AdminRentedInventoryAdapter(Context applicationContext, List<RentedInventoryResponse> rentedInventoryResponseList) {
        this.inflater = LayoutInflater.from(applicationContext);
        this.rentedInventoryResponseList = rentedInventoryResponseList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, surname, item, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvAdminUsersRowName);
            surname = itemView.findViewById(R.id.RItxtRecycleViewUserSURNAME1);
            item = itemView.findViewById(R.id.RIRecycleViewITEM);
            amount = itemView.findViewById(R.id.RItxtRecycleViewAMOUNT);
        }
    }

    @NonNull
    @Override
    public AdminRentedInventoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_currently_rented_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRentedInventoryAdapter.MyViewHolder holder, int position) {

        holder.name.setText(rentedInventoryResponseList.get(position).getName());
        holder.surname.setText(rentedInventoryResponseList.get(position).getSurname());
        holder.item.setText(rentedInventoryResponseList.get(position).getEquipment());
        holder.amount.setText(rentedInventoryResponseList.get(position).getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return rentedInventoryResponseList.size();
    }
}
