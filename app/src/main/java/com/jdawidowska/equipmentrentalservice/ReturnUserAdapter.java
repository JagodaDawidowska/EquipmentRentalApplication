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

public class ReturnUserAdapter extends RecyclerView.Adapter<ReturnUserAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<ReturnUserResponse> returnUserResponseList;
    private OnItemClickListener onItemClickListener;;


    public interface OnItemClickListener{

        void onReturnButton(int position);
    }

    // setter to interface to make button clickable
    public void setOnItemClickListener(ReturnUserAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ReturnUserAdapter(Context context, ArrayList<ReturnUserResponse> returnUserResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.returnUserResponseList = returnUserResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_user_return_row,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.item.setText(returnUserResponseList.get(position).getName());
        holder.amount.setText(returnUserResponseList.get(position).getAmount().toString());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item, amount;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txtItemRetunRow);
            amount = itemView.findViewById(R.id.txtAmountReturnRow);
            button = itemView.findViewById(R.id.btnReturnUserRow);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                           onItemClickListener.onReturnButton(position);
                        }
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return returnUserResponseList.size();
    }


}
