package com.jdawidowska.equipmentrentalservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<UserHistoryResponse> userHistoryResponseList;

    public HistoryAdapter(Context context, ArrayList<UserHistoryResponse> userHistoryResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.userHistoryResponseList = userHistoryResponseList;
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
        holder.email.setText(userHistoryResponseList.get(position).getEmail());
        holder.item.setText(userHistoryResponseList.get(position).getItemName());
        holder.rentDate.setText(userHistoryResponseList.get(position).getRentDate().toString());
        if(userHistoryResponseList.get(position).getReturnDate() != null){
            holder.returnDate.setText(userHistoryResponseList.get(position).getReturnDate().toString());
        } else {
            holder.returnDate.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return userHistoryResponseList.size();
    }
}
