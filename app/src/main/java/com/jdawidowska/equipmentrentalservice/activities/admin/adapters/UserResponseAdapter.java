package com.jdawidowska.equipmentrentalservice.activities.admin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserResponse;

import java.util.List;

public class UserResponseAdapter extends RecyclerView.Adapter<UserResponseAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<UserResponse> userResponseList;
    public OnItemClickListener onItemClickListener;
    public OnItemTouchListener onItemTouchListener;


    public UserResponseAdapter(Context ctx, List<UserResponse> userResponseList){
        this.inflater = LayoutInflater.from(ctx);
        this.userResponseList = userResponseList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    public interface OnItemClickListener{
        void onIdClicked(int position);
    }

    public interface  OnItemTouchListener{
        void onIdTouch(int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, name, surname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtRecycleViewHistoryAdminEmail);
            name = itemView.findViewById(R.id.txtRecycleViewHistoryAdminReturn);
            surname = itemView.findViewById(R.id.txtRecycleViewSURNAME);

            id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onIdClicked(position);
                        }
                    }
                }
            });

           id.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   if (onItemTouchListener != null) {
                       int position = getAbsoluteAdapterPosition();
                       if (position != RecyclerView.NO_POSITION) {
                           onItemTouchListener.onIdTouch(position);
                           id.setTextColor(Color.parseColor("#00CC6A"));
                           if(event.getAction() == MotionEvent.ACTION_MOVE)
                           {
                               id.setTextColor(Color.parseColor("#00CC6A"));
                           }
                       }
                   }
                   return false;
               }
           });
        }
    }

    @NonNull
    @Override
    public UserResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleview_users_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserResponseAdapter.ViewHolder holder, int position) {
        holder.id.setText(userResponseList.get(position).getId().toString());
        holder.name.setText(userResponseList.get(position).getName());
        holder.surname.setText(userResponseList.get(position).getSurname());
    }

    @Override
    public int getItemCount() {
        return userResponseList.size();
    }
}
