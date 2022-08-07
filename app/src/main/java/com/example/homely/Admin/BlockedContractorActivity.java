package com.example.homely.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.homely.Adapter.AdminContractorAdapter;
import com.example.homely.Contractor.CheckBidingActivity;
import com.example.homely.Contractor.ContractorDashboardActivity;
import com.example.homely.LoginActivity;
import com.example.homely.Model.Data;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlockedContractorActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    AdminContractorAdapter postJobAdapter;
    FirebaseAuth auth;
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_contractor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //3

        recyclerView = findViewById(R.id.recycler_blocked_contractor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();

        mJobPost = FirebaseDatabase.getInstance().getReference().child("User");

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mJobPost.orderByChild("userTypes").equalTo(3), Data.class)
                        .build();

        postJobAdapter = new AdminContractorAdapter(options);
        recyclerView.setAdapter(postJobAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        postJobAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        postJobAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent intent = new Intent(BlockedContractorActivity.this, AdminContractorActivity.class);
            startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent = new Intent(BlockedContractorActivity.this, AdminContractorActivity.class);
        startActivity(intent);
        return true;
    }

}