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
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentedInventoryResponse;

import java.util.List;

public class UserCurrentlyRentedAdapter extends RecyclerView.Adapter<UserCurrentlyRentedAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final List<UserRentedInventoryResponse> userRentedInventoryResponseList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onReturnButton(int position);
    }

    // setter to interface to make button clickable
    public void setOnItemClickListener(UserCurrentlyRentedAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public UserCurrentlyRentedAdapter(Context context, List<UserRentedInventoryResponse> userRentedInventoryResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.userRentedInventoryResponseList = userRentedInventoryResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_currently_rented_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemNameAndAmount.setText(formatText(position));
    }

    private String formatText(int itemPosition) {
        UserRentedInventoryResponse rentedItem = userRentedInventoryResponseList.get(itemPosition);
        return rentedItem.getName() + " " + rentedItem.getAmount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameAndAmount;
        Button bReturnItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameAndAmount = itemView.findViewById(R.id.txtItemRetunRow);
            bReturnItem = itemView.findViewById(R.id.btnReturnItem);
            bReturnItem.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onReturnButton(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userRentedInventoryResponseList.size();
    }
}
