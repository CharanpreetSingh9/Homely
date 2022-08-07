package com.example.homely.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.homely.Adapter.AdminPostAdapter;
import com.example.homely.Adapter.PostProjectAdapter;
import com.example.homely.Model.PostProject;
import com.example.homely.R;
import com.example.homely.myViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPostActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminPostAdapter postJobAdapter;
    FirebaseAuth auth;
    private DatabaseReference mJobPost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post);
        getSupportActionBar().setTitle("All Post");

        recyclerView = findViewById(R.id.recycler_admin_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();

        mJobPost = FirebaseDatabase.getInstance().getReference().child("Project Post");

        FirebaseRecyclerOptions<PostProject> options =
                new FirebaseRecyclerOptions.Builder<PostProject>()
                        .setQuery(mJobPost, PostProject.class)
                        .build();
        postJobAdapter = new AdminPostAdapter(options);
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
}