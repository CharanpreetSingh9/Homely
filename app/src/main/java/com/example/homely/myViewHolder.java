package com.example.homely;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder{
    public TextView name;
    public TextView area;
    public TextView budget;
    public TextView location;
    public TextView date;
    public TextView material;
    public LinearLayout btn_bid;
    public TextView end_date;
    public TextView posted_user_name;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);


        end_date = itemView.findViewById(R.id.end_datetxt);
        posted_user_name = itemView.findViewById(R.id.posted_user_name);
        material = itemView.findViewById(R.id.materialtxt);
        name = itemView.findViewById(R.id.projectnamext);
        budget = itemView.findViewById(R.id.budgettxt);
        area = itemView.findViewById(R.id.areatxt);
        date = itemView.findViewById(R.id.datetxt);
        location = itemView.findViewById(R.id.locationtxt);

        btn_bid = itemView.findViewById(R.id.btn_bid);

    }
}