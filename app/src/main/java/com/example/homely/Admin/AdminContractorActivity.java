package com.example.homely.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homely.Adapter.AdminContractorAdapter;
import com.example.homely.Adapter.AdminPostAdapter;
import com.example.homely.Model.Data;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminContractorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminContractorAdapter postJobAdapter;
    FirebaseAuth auth;
    Button btn_cont_blocked;
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_contractor);
        getSupportActionBar().setTitle("Contractors");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_admin_contractor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_cont_blocked = (Button) findViewById(R.id.btn_cont_blocked);

        btn_cont_blocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminContractorActivity.this,BlockedContractorActivity.class));
            }
        });
        auth = FirebaseAuth.getInstance();

        mJobPost = FirebaseDatabase.getInstance().getReference().child("User");

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mJobPost.orderByChild("userTypes").equalTo(0), Data.class)
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
        startActivity(new Intent(AdminContractorActivity.this,AdminDashboardActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        startActivity(new Intent(AdminContractorActivity.this,AdminDashboardActivity.class));
        return true;
    }
}