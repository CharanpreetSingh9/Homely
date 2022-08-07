package com.example.homely.Contractor;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homely.Chat.ChattingActivity;
import com.example.homely.Model.BidingData;
import com.example.homely.Model.Global;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AcceptedBidingAdapter extends FirebaseRecyclerAdapter<BidingData, AcceptedBidingAdapter.myViewHolder> {



    public AcceptedBidingAdapter(@NonNull FirebaseRecyclerOptions<BidingData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull BidingData model) {
        holder.cname.setText(model.getCompanyName());
        holder.username.setText(model.getFullname());
        holder.projectname.setText("Bid For: "+model.getProjectName());
        holder.amount.setText("Place Bid at "+model.getAmount()+" CAD");
        holder.date.setText("Bid Date: "+model.getDate());

        if (Objects.equals(model.getStatus(), "accepted")){
            holder.accept.setText("Accepted");

            holder.reject.setVisibility(View.GONE);

        }if (Objects.equals(model.getStatus(), "rejected")){
            holder.reject.setText("Rejected");
            holder.accept.setVisibility(View.GONE);
        }




        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.cname.getContext(), ChattingActivity.class);
                intent.putExtra("receiverId",model.getId());
                intent.putExtra("projectId",getRef(position).getKey());
                intent.putExtra("projectName",model.getProjectName());
                intent.putExtra("userName",model.getFullname());
                holder.cname.getContext().startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(model.getStatus(), "pending")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", "rejected");

                    FirebaseDatabase.getInstance().getReference().child("Project Post").child(Global.BIDING_PUSH_ID)
                            .child("Biding").child(model.getId()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    holder.reject.setText("Rejected");
                                    holder.accept.setVisibility(View.GONE);


                                    FirebaseDatabase.getInstance().getReference("User").child(model.getId()).child("Bid")
                                            .child(Global.BIDING_PUSH_ID).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(holder.cname.getContext(), "Rejected", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(holder.cname.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(holder.cname.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else if (Objects.equals(model.getStatus(), "accepted")){
                    holder.accept.setText("Accepted");
                    holder.reject.setVisibility(View.GONE);

                    Toast.makeText(holder.cname.getContext(), "Already Accepted", Toast.LENGTH_SHORT).show();
                }else {
                    holder.reject.setText("Rejected");
                    holder.accept.setVisibility(View.GONE);

                    Toast.makeText(holder.cname.getContext(), "Already Rejected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (Objects.equals(model.getStatus(), "pending")) {
                    Toast.makeText(holder.cname.getContext(), model.getStatus(), Toast.LENGTH_SHORT).show();
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", "accepted");

                    FirebaseDatabase.getInstance().getReference().child("Project Post").child(Global.BIDING_PUSH_ID)
                            .child("Biding").child(model.getId()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    holder.accept.setText("Accepted");
                                    holder.reject.setVisibility(View.GONE);
                                    Map<String,Object>map1 = new HashMap<>();
                                    map1.put("status","accepted");
                                    FirebaseDatabase.getInstance().getReference("User").child(model.getId()).child("Bid")
                                            .child(Global.BIDING_PUSH_ID).updateChildren(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("kkkkkh",Global.BIDING_PUSH_ID);
                                                    Log.d("kkkkkyy",model.getId());
                                                    Toast.makeText(holder.cname.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(holder.cname.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(holder.cname.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else if (Objects.equals(model.getStatus(), "Accepted")){
                    holder.accept.setText("Accepted");
                    holder.reject.setVisibility(View.GONE);

                    Toast.makeText(holder.cname.getContext(), "Already Accepted", Toast.LENGTH_SHORT).show();
                }else {
                    holder.reject.setText("Rejected");
                    holder.accept.setVisibility(View.GONE);

                    Toast.makeText(holder.cname.getContext(), "Already Rejected", Toast.LENGTH_SHORT).show();

                }

            }
        });
        */

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.biding_layout,parent,false);
        return new AcceptedBidingAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView cname,username,amount,projectname,date;
        TextView accept,reject,chat;
        CardView cardView;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            cname = itemView.findViewById(R.id.bid_cname);
            username = itemView.findViewById(R.id.bid_fullname);
            amount = itemView.findViewById(R.id.bid_amount);
            projectname = itemView.findViewById(R.id.bid_project);
            date = itemView.findViewById(R.id.bid_date);
            cardView = itemView.findViewById(R.id.card_biding);
            accept = itemView.findViewById(R.id.bid_accept);
            reject = itemView.findViewById(R.id.bid_reject);
            chat = itemView.findViewById(R.id.btn_chat);



        }
    }
}
