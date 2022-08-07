package com.example.homely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homely.Chat.ChatInterfaceActivity;
import com.example.homely.Contractor.ContractorDashboardActivity;
import com.example.homely.Model.Data;
import com.example.homely.User.UserDashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView profile_name , profile_email, company_name , company_found , company_build,logout,phone;
    LinearLayout linearLayout;
    FloatingActionButton fab_edit;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();


        fab_edit = (FloatingActionButton) findViewById(R.id.fab_edit);
        linearLayout = (LinearLayout) findViewById(R.id.linear_profile);
        profile_name = (TextView) findViewById(R.id.profile_fullname);
        profile_email = (TextView) findViewById(R.id.profile_email);
        company_name = (TextView) findViewById(R.id.profile_com_name);
        company_found = (TextView) findViewById(R.id.profile_com_found);
        company_build = (TextView) findViewById(R.id.profile_com_built);
        phone = (TextView) findViewById(R.id.profile_phone);
        logout = (TextView) findViewById(R.id.profile_logout);




        if (LoginActivity.userTypes == 0){
            linearLayout.setVisibility(View.VISIBLE);
            FirebaseDatabase.getInstance().getReference().child("User")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            data = snapshot.getValue(Data.class);

                            Log.d("lllkk",data.getFullname());

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
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }else{
            linearLayout.setVisibility(View.GONE);
            FirebaseDatabase.getInstance().getReference().child("User")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            data = snapshot.getValue(Data.class);


                            String name = snapshot.child("fullname").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String phoneno = snapshot.child("phoneno").getValue(String.class);

                            profile_name.setText(name);
                            profile_email.setText(email);
                            phone.setText(phoneno);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }







        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(ProfileActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.update_profile_form))
                        .setExpanded(true,1500)
                        .create();


                View view1 = dialogPlus.getHolderView();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                EditText name = view1.findViewById(R.id.update_fullname);
                EditText phone = view1.findViewById(R.id.update_phone);
                EditText companyname = view1.findViewById(R.id.update_coname);
                EditText companyfound = view1.findViewById(R.id.update_cofound);
                EditText housebuild = view1.findViewById(R.id.update_house);
                LinearLayout linearLayout1 = view1.findViewById(R.id.update_linear);


                name.setText(data.getFullname());
                phone.setText(data.getPhoneno());
                companyname.setText(data.getCompanyName());
                companyfound.setText(data.getCompanyFound());
                housebuild.setText(data.getCompanyBuild());


                if (LoginActivity.userTypes==0){
                    linearLayout1.setVisibility(View.VISIBLE);
                }

                Button update = view1.findViewById(R.id.btn_update);
                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (LoginActivity.userTypes==0) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("fullname",name.getText().toString().trim());
                            map.put("phoneno",phone.getText().toString().trim());
                            map.put("companyName",companyname.getText().toString().trim());
                            map.put("companyFound",companyfound.getText().toString().trim());
                            map.put("companyBuild",housebuild.getText().toString().trim());

                            FirebaseDatabase.getInstance().getReference().child("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfileActivity.this, "details Update Sucessfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfileActivity.this, "Error in update details", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }else {

                            Map<String, Object> map = new HashMap<>();
                            map.put("fullname",name.getText().toString().trim());
                            map.put("phoneno",phone.getText().toString().trim());


                            FirebaseDatabase.getInstance().getReference().child("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfileActivity.this, "details Update Sucessfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfileActivity.this, "Error in update details", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });

                        }

                    }
                });


            }
        });






        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.rbottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.rprofile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home:
                        if (LoginActivity.userTypes==0) {
                            startActivity(new Intent(ProfileActivity.this, UserDashboardActivity.class));
                        }else {
                            startActivity(new Intent(ProfileActivity.this, ContractorDashboardActivity.class));
                        }
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
    public void onBackPressed() {

        super.onBackPressed();

        if (LoginActivity.userTypes==0) {
            startActivity(new Intent(ProfileActivity.this, UserDashboardActivity.class));
        }else {
            startActivity(new Intent(ProfileActivity.this, ContractorDashboardActivity.class));
        }
    }
}