package com.example.homely.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homely.Model.BidStatus;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StatusAdapter extends FirebaseRecyclerAdapter<BidStatus, StatusAdapter.myViewHolder> {



    public StatusAdapter(@NonNull FirebaseRecyclerOptions<BidStatus> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull BidStatus model) {

        Log.d("kkkkyy",model.getUserName());
        holder.username.setText(model.getUserName());
        holder.amount.setText("Place Bid at "+model.getAmount()+" CAD");
        holder.projectname.setText(model.getProjectName());
        holder.status.setText(model.getStatus());
        holder.date.setText("Bid Date: "+model.getDate());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_status_layout,parent,false);
        return new StatusAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView amount,status,date,username,projectname;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.amount_bid_user);
            status = itemView.findViewById(R.id.status_bid);
            date = itemView.findViewById(R.id.bid_date_user);
            username = itemView.findViewById(R.id.user_name_bid);
            projectname = itemView.findViewById(R.id.project_name_bid);



        }
    }
}
