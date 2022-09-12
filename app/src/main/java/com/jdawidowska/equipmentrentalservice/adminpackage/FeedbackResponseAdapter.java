package com.jdawidowska.equipmentrentalservice.adminpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.FeedbackResponseDTO;

import java.util.ArrayList;

public class FeedbackResponseAdapter extends RecyclerView.Adapter<FeedbackResponseAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ArrayList<FeedbackResponseDTO> feedbackResponseDTOList;

    public FeedbackResponseAdapter(Context context, ArrayList<FeedbackResponseDTO> feedbackResponseDTOList) {
        this.inflater = LayoutInflater.from(context);
        this.feedbackResponseDTOList = feedbackResponseDTOList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email, content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.FRecycleViewEMAIL);
            content = itemView.findViewById(R.id.FRecycleViewCONTENT);
        }
    }

    @NonNull
    @Override
    public FeedbackResponseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_feedback_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackResponseAdapter.MyViewHolder holder, int position) {
        holder.email.setText(feedbackResponseDTOList.get(position).getEmail());
        holder.content.setText(feedbackResponseDTOList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return feedbackResponseDTOList.size() ;
    }
}
