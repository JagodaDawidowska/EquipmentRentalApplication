package com.jdawidowska.equipmentrentalservice.activities.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.dto.response.FeedbackResponse;

import java.util.List;

public class AdminFeedbacksAdapter extends RecyclerView.Adapter<AdminFeedbacksAdapter.MyViewHolder> {

    LayoutInflater inflater;
    List<FeedbackResponse> feedbackResponseList;

    public AdminFeedbacksAdapter(Context context, List<FeedbackResponse> feedbackResponseList) {
        this.inflater = LayoutInflater.from(context);
        this.feedbackResponseList = feedbackResponseList;
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
    public AdminFeedbacksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_feedback_row, parent, false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFeedbacksAdapter.MyViewHolder holder, int position) {
        holder.email.setText(feedbackResponseList.get(position).getEmail());
        holder.content.setText(feedbackResponseList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return feedbackResponseList.size() ;
    }
}
