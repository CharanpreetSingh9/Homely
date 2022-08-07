package com.example.homely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.homely.Adapter.PostProjectAdapter;
import com.example.homely.Adapter.StatusAdapter;
import com.example.homely.Model.BidStatus;
import com.example.homely.Model.PostProject;
import com.example.homely.User.UserDashboardActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BidStatusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StatusAdapter statusAdapter;
    FirebaseAuth auth;
    TextView txt_bid;
    List<BidStatus>list = new ArrayList<>();
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_status);

        txt_bid = (TextView) findViewById(R.id.txt_bid);
        recyclerView = findViewById(R.id.recycler_status);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.chat:

                        startActivity(new Intent(getApplicationContext(), BidStatusActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return true;

                }
                return false;
            }
        });

        mJobPost = FirebaseDatabase.getInstance().getReference().child("User").child(auth.getCurrentUser().getUid())
                .child("Bid");

        mJobPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    BidStatus bidStatus = snapshot.getValue(BidStatus.class);
                    list.add(bidStatus);
                }
                if (list.isEmpty()){
                    txt_bid.setVisibility(View.VISIBLE);
                }else {
                    txt_bid.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<BidStatus> options =
                new FirebaseRecyclerOptions.Builder<BidStatus>()
                        .setQuery(mJobPost, BidStatus.class)
                        .build();
        statusAdapter = new StatusAdapter(options);
        recyclerView.setAdapter(statusAdapter);
    }



    @Override
    protected void onStart() {
        super.onStart();
        statusAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        statusAdapter.stopListening();
    }
}