package com.example.homely.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.homely.Adapter.ChatUserAdapter;
import com.example.homely.Contractor.ContractorDashboardActivity;
import com.example.homely.LoginActivity;
import com.example.homely.Model.Data;
import com.example.homely.R;
import com.example.homely.User.UserDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatInterfaceActivity extends AppCompatActivity {

   List<Data>list = new ArrayList<>();

    FirebaseDatabase database;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_interface);

        recyclerView = findViewById(R.id.recycler_chat_user);

        ChatUserAdapter chatUserAdapter = new ChatUserAdapter(list,getApplicationContext());
        recyclerView.setAdapter(chatUserAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();

        if (LoginActivity.userTypes==0){

            Query query = database.getReference().child("User").orderByChild("userTypes").equalTo(1);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(ChatInterfaceActivity.this, "exist", Toast.LENGTH_SHORT).show();
                            list.clear();
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            // do with your result

                            Data data = issue.getValue(Data.class);
                            list.add(data);

                            Log.d("yyhh",data.getFullname());
                        }
                        chatUserAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            Toast.makeText(this, "user 0", Toast.LENGTH_SHORT).show();
            Query query1 = database.getReference().child("User").orderByChild("userTypes").equalTo(0);
            Toast.makeText(this, "query", Toast.LENGTH_SHORT).show();

            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(ChatInterfaceActivity.this, "exist", Toast.LENGTH_SHORT).show();
                        list.clear();
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            // do with your result

                            Data data = issue.getValue(Data.class);
                            list.add(data);

                            Log.d("yyhh",data.getFullname());
                        }
                        chatUserAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(ChatInterfaceActivity.this, "not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }





    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (LoginActivity.userTypes==0) {
            startActivity(new Intent(ChatInterfaceActivity.this, UserDashboardActivity.class));
        }else {
            startActivity(new Intent(ChatInterfaceActivity.this, ContractorDashboardActivity.class));
        }
    }
}