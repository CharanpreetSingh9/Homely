package com.example.homely.Contractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homely.Adapter.PostProjectAdapter;
import com.example.homely.Chat.ChatInterfaceActivity;
import com.example.homely.Chat.ChattingActivity;
import com.example.homely.LoginActivity;
import com.example.homely.MainActivity;
import com.example.homely.Model.Global;
import com.example.homely.Model.PostProject;
import com.example.homely.ProfileActivity;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContractorDashboardActivity extends AppCompatActivity {

    FloatingActionButton fabBtn;
    TextView txt_yet;

    RecyclerView recyclerView;
    PostProjectAdapter postJobAdapter;
    FirebaseAuth auth;
    List<PostProject>list = new ArrayList<>();
    private DatabaseReference mJobPost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_dashboard);
        getSupportActionBar().setTitle("Your Projects");

        fabBtn = findViewById(R.id.fab_add);

        txt_yet = (TextView) findViewById(R.id.txt_yet);

        recyclerView = findViewById(R.id.recycler_contractor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();

        mJobPost = FirebaseDatabase.getInstance().getReference().child("Project Post");

        mJobPost.orderByChild("id").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    PostProject project = snapshot.getValue(PostProject.class);
                    list.add(project);
                }
                if (list.isEmpty()){
                    txt_yet.setVisibility(View.VISIBLE);
                }else {
                    txt_yet.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<PostProject> options =
                new FirebaseRecyclerOptions.Builder<PostProject>()
                        .setQuery(mJobPost.orderByChild("id").equalTo(auth.getUid()), PostProject.class)
                        .build();
        postJobAdapter = new PostProjectAdapter(options);
        recyclerView.setAdapter(postJobAdapter);






        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContractorDashboardActivity.this,PostActivity.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.rbottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home:
                        return true;
                    case R.id.rprofile:

                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return true;



                }
                return false;
            }
        });




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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        MenuItem logout = menu.findItem(R.id.lagout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                finish();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}