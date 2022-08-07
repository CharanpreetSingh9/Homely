package com.example.homely.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homely.Adapter.PostProjectAdapter;
import com.example.homely.BidStatusActivity;
import com.example.homely.Chat.ChatInterfaceActivity;
import com.example.homely.Chat.ChattingActivity;
import com.example.homely.LoginActivity;
import com.example.homely.MainActivity;
import com.example.homely.Model.BidStatus;
import com.example.homely.Model.Data;
import com.example.homely.Model.Global;
import com.example.homely.Model.PostProject;
import com.example.homely.ProfileActivity;
import com.example.homely.R;
import com.example.homely.myViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserDashboardActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView recyclerView;
    private DatabaseReference mJob,mDatabase;
    FirebaseDatabase database;
    public static Data userData;

    FirebaseRecyclerAdapter<PostProject, myViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        getSupportActionBar().setTitle("All Projects");

        recyclerView = findViewById(R.id.recycler_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();
        mJob = FirebaseDatabase.getInstance().getReference().child("Project Post");

        database = FirebaseDatabase.getInstance();

        database.getReference().child("User").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userData = snapshot.getValue(Data.class);
                        }else {
                            Toast.makeText(UserDashboardActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.chat:

                        startActivity(new Intent(getApplicationContext(), BidStatusActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return true;

                }
                return false;
            }
        });

        FirebaseRecyclerOptions<PostProject> options =
                new FirebaseRecyclerOptions.Builder<PostProject>()
                        .setQuery(mJob, PostProject.class)
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostProject, myViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull PostProject model) {

                holder.name.setText(model.getName());
                holder.area.setText(model.getArea()+"  Sq");
                holder.budget.setText(model.getBudget()+"  CAD");
                holder.location.setText(model.getLocation());
                holder.date.setText("posted :\t"+model.getDate());
                holder.material.setText(model.getMaterial()+ " Material");

                holder.end_date.setText("Bid End Date: "+model.getEndDate());
                holder.posted_user_name.setText("Posted by: "+model.getPostUser());

                holder.btn_bid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = model.getId();
                        Global.BID_AVAILABLE = "yes";
                        Intent intent = new Intent(UserDashboardActivity.this, ChattingActivity.class);
                        intent.putExtra("receiverId",id);
                        intent.putExtra("projectName",model.getName());
                        intent.putExtra("projectId",model.getPushid());
                        intent.putExtra("userName",model.getPostUser());


                        startActivity(intent);
                    }
                });

            }


            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_poset_item,parent,false);
                return new myViewHolder(view);

            }
        } ;

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);


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