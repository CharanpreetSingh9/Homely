package com.example.homely.Contractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homely.Adapter.BidingAdapter;
import com.example.homely.Admin.AdminContractorActivity;
import com.example.homely.Admin.BlockedContractorActivity;
import com.example.homely.Model.BidStatus;
import com.example.homely.Model.BidingData;
import com.example.homely.Model.Global;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BidAcceptedActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    AcceptedBidingAdapter BidingAdapter;
    FirebaseAuth auth;
    Button accepted_bid;
    TextView txt_bid_status;
    List<BidStatus> list = new ArrayList<>();
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_accepted);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_bid_status = (TextView) findViewById(R.id.txt_accepted);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_accepted_bid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mJobPost = FirebaseDatabase.getInstance().getReference().child("Project Post")
                .child(Global.BIDING_PUSH_ID).child("Biding");

        mJobPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    BidStatus bidStatus = snapshot.getValue(BidStatus.class);
                    list.add(bidStatus);
                }
                if (list.isEmpty()){
                    txt_bid_status.setVisibility(View.VISIBLE);
                }else {
                   txt_bid_status.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<BidingData> options =
                new FirebaseRecyclerOptions.Builder<BidingData>()

                        .setQuery(mJobPost.orderByChild("status").equalTo("accepted"), BidingData.class)
                        .build();

        BidingAdapter = new AcceptedBidingAdapter(options);
        recyclerView.setAdapter(BidingAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        BidingAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        BidingAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BidAcceptedActivity.this,ContractorDashboardActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent = new Intent(BidAcceptedActivity.this, ContractorDashboardActivity.class);
        startActivity(intent);
        return true;
    }
}