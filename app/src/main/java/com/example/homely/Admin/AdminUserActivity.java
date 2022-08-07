package com.example.homely.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homely.Adapter.AdminContractorAdapter;
import com.example.homely.Adapter.AdminUserAdapter;
import com.example.homely.Model.Data;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminUserAdapter postJobAdapter;
    FirebaseAuth auth;
    Button button;
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        getSupportActionBar().setTitle("Users");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button = (Button) findViewById(R.id.btn_user_blocked);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminUserActivity.this,BlockedUserActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_admin_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();

        mJobPost = FirebaseDatabase.getInstance().getReference().child("User");




        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mJobPost.orderByChild("userTypes").equalTo(1), Data.class)
                        .build();

        postJobAdapter = new AdminUserAdapter(options);
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
        startActivity(new Intent(AdminUserActivity.this,AdminDashboardActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        startActivity(new Intent(AdminUserActivity.this,AdminDashboardActivity.class));
        return true;
    }
}