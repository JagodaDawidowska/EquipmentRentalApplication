package com.jdawidowska.equipmentrentalservice.activities.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentingHistoryResponse;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<UserRentingHistoryResponse> userRentingHistoryResponseList;

    public HistoryAdapter(Context context, ArrayList<UserRentingHistoryResponse> userRentingHistoryResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.userRentingHistoryResponseList = userRentingHistoryResponseList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email, item, rentDate, returnDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.txtRecycleViewHistoryAdminEmail);
            item = itemView.findViewById(R.id.txtRecycleViewHistoryAdminItem);
            rentDate = itemView.findViewById(R.id.txtRecycleViewHistoryAdminRent);
            returnDate = itemView.findViewById(R.id.txtRecycleViewHistoryAdminReturn);
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_response_by_id_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        holder.email.setText(userRentingHistoryResponseList.get(position).getEmail());
        holder.item.setText(userRentingHistoryResponseList.get(position).getItemName());
        holder.rentDate.setText(userRentingHistoryResponseList.get(position).getRentDate().toString());
        if(userRentingHistoryResponseList.get(position).getReturnDate() != null){
            holder.returnDate.setText(userRentingHistoryResponseList.get(position).getReturnDate().toString());
        } else {
            holder.returnDate.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return userRentingHistoryResponseList.size();
    }
}
