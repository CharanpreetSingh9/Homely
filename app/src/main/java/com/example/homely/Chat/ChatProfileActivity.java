package com.example.homely.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homely.Model.Global;
import com.example.homely.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatProfileActivity extends AppCompatActivity {

    TextView profile_name , profile_email, company_name , company_found , company_build,phone;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_profile);

        getSupportActionBar().hide();

        linearLayout = (LinearLayout) findViewById(R.id.linear_profile);
        profile_name = (TextView) findViewById(R.id.profile_fullname);
        profile_email = (TextView) findViewById(R.id.profile_email);
        company_name = (TextView) findViewById(R.id.profile_com_name);
        company_found = (TextView) findViewById(R.id.profile_com_found);
        company_build = (TextView) findViewById(R.id.profile_com_built);
        phone = (TextView) findViewById(R.id.profile_phone);


        FirebaseDatabase.getInstance().getReference().child("User")
                .child(Global.RECEIVER_ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("companyName").exists()){
                            linearLayout.setVisibility(View.VISIBLE);
                            String name = snapshot.child("fullname").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String companyName = snapshot.child("companyName").getValue(String.class);
                            String companyFound = snapshot.child("companyFound").getValue(String.class);
                            String companyBuild = snapshot.child("companyBuild").getValue(String.class);
                            String phoneno = snapshot.child("phoneno").getValue(String.class);

                            profile_name.setText(name);
                            profile_email.setText(email);
                            phone.setText(phoneno);
                            company_name.setText(companyName);
                            company_found.setText(companyFound);
                            company_build.setText(companyBuild);
                        }else {
                            linearLayout.setVisibility(View.GONE);
                            String name = snapshot.child("fullname").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String phoneno = snapshot.child("phoneno").getValue(String.class);

                            profile_name.setText(name);
                            profile_email.setText(email);
                            phone.setText(phoneno);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}