package com.jdawidowska.equipmentrentalservice.adminpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.UserResponse;

import java.util.List;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<UserResponse> userResponseList;

    public Adapter(Context ctx, List<UserResponse> userResponseList){
        this.inflater = LayoutInflater.from(ctx);
        this.userResponseList = userResponseList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, name, surname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtRecycleViewUserID);
            name = itemView.findViewById(R.id.txtRecycleViewUserNAME);
            surname = itemView.findViewById(R.id.txtRecycleViewSURNAME);
        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleview_users_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.id.setText(userResponseList.get(position).getId().toString());
        holder.name.setText(userResponseList.get(position).getName());
        holder.surname.setText(userResponseList.get(position).getSurname());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
