package com.example.homely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homely.Admin.AdminDashboardActivity;
import com.example.homely.Contractor.ContractorDashboardActivity;
import com.example.homely.Model.Global;
import com.example.homely.User.UserDashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public static  int userTypes = 0;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();



    EditText email , password;
    TextView txt_new_account,txt_forget_pass;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Global.BID_AVAILABLE = "no";

        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        txt_forget_pass = (TextView) findViewById(R.id.txt_forget_pass);
        txt_new_account = (TextView) findViewById(R.id.txt_create_new_account);
        btn_login = (Button) findViewById(R.id.btn_login);



        if (auth.getCurrentUser() != null) {

            FirebaseUser mUser = auth.getCurrentUser();
            String uid = mUser.getUid();
            firebaseDatabase.getReference().child("User").child(uid).child("userTypes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     userTypes = snapshot.getValue(Integer.class);
                    if (userTypes == 1) {

                        startActivity(new Intent(getApplicationContext(),ContractorDashboardActivity.class));

                    }
                    if (userTypes == 0) {
                        startActivity(new Intent(getApplicationContext(),UserDashboardActivity.class));
                    }
                    if (userTypes == 2) {
                        Intent in = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        startActivity(in);
                    }if (userTypes == 3){
                        Toast.makeText(LoginActivity.this, "Your Account Is Blocked For Some Reason", Toast.LENGTH_LONG).show();
                    }

                    if (userTypes == 4){
                        Toast.makeText(LoginActivity.this, "Your Account Is Blocked For Some Reason", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        txt_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

        // Login the firebase code
    }

    public void Login() {
        String emailid = email.getText().toString().trim();
        String passw = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailid)){
            email.setError("email id is required");
            return;
        }
        if (TextUtils.isEmpty(passw)){
            password.setError("password is empty");
            return;
        }
        if (passw.length()<8){
            password.setError("password length must be 8 char long");
            return;
        }
        auth.signInWithEmailAndPassword(emailid,passw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                String uid = authResult.getUser().getUid();
                firebaseDatabase.getReference().child("User").child(uid).child("userTypes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userTypes = snapshot.getValue(Integer.class);
                        if (userTypes == 0){
                            Intent in = new Intent(LoginActivity.this, UserDashboardActivity.class);
                            startActivity(in);
                            finish();
                        }
                        if (userTypes == 1){
                            Intent in = new Intent(LoginActivity.this, ContractorDashboardActivity.class);
                            startActivity(in);
                            finish();
                        }
                        if (userTypes == 2){
                            Intent in = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                            startActivity(in);
                            finish();
                        }if (userTypes == 3){
                            Toast.makeText(LoginActivity.this, "Your Account Is Blocked For Some Reason", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        if (userTypes == 4){
                            Toast.makeText(LoginActivity.this, "Your Account Is Blocked For Some Reason", Toast.LENGTH_LONG).show();
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}