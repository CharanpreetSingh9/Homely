package com.example.homely.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homely.Adapter.ContractorAdapter;
import com.example.homely.Login;
import com.example.homely.Model.Data;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminContractors extends AppCompatActivity {
    RecyclerView recyclerView;
    ContractorAdapter contractorAdapter;
    FirebaseAuth auth;
    private DatabaseReference mJobPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_applicant);
        getSupportActionBar().setTitle("Contractor Data");
        recyclerView = findViewById(R.id.rvaa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();
        FirebaseUser mUser = auth.getCurrentUser();
        String uid = mUser.getUid();
        mJobPost = FirebaseDatabase.getInstance().getReference().child("User");
        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mJobPost.orderByChild("userTypes").equalTo(0), Data.class)
                        .build();
        contractorAdapter = new ContractorAdapter(options);
        recyclerView.setAdapter(contractorAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        contractorAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        contractorAdapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        MenuItem logoutitem = menu.findItem(R.id.lagout);
        logoutitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}