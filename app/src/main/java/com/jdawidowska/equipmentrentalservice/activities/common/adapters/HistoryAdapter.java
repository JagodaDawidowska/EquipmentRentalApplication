package com.jdawidowska.equipmentrentalservice.activities.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentingHistoryResponse;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    LayoutInflater inflater;
    List<UserRentingHistoryResponse> userRentingHistoryResponseList;

    public HistoryAdapter(Context context, List<UserRentingHistoryResponse> userRentingHistoryResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.userRentingHistoryResponseList = userRentingHistoryResponseList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item, rentDate, returnDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtRecycleViewHistoryAdminItem);
            rentDate = itemView.findViewById(R.id.txtRecycleViewHistoryAdminRent);
            returnDate = itemView.findViewById(R.id.tvAdminUsersRowName);
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_history_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        holder.item.setText(userRentingHistoryResponseList.get(position).getItemName());
        holder.rentDate.setText("Rent date: " + userRentingHistoryResponseList.get(position).getRentDate().toString());
        if(userRentingHistoryResponseList.get(position).getReturnDate() != null){
            holder.returnDate.setText("Return date: " + userRentingHistoryResponseList.get(position).getReturnDate().toString());
        } else {
            holder.returnDate.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return userRentingHistoryResponseList.size();
    }
}
