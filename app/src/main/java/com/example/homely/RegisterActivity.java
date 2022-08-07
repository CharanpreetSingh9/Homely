package com.example.homely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homely.Model.Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    EditText name,email,password,cpassword,phoneno,companyname,companyfound,built;
    CheckBox user,contractor;
    int types;

    TextView txt_login;

    Button btn_register;
    LinearLayout aditional_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        aditional_linear = (LinearLayout) findViewById(R.id.aditional_linear);
        txt_login = findViewById(R.id.txt_login);
        companyname = (EditText) findViewById(R.id.register_companyname);
        companyfound = (EditText) findViewById(R.id.register_company_year);
        built = (EditText) findViewById(R.id.register_build);
        name = findViewById(R.id.register_fullname);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        cpassword = findViewById(R.id.register_conpass);
        phoneno = findViewById(R.id.register_phone_no);
        contractor = findViewById(R.id.contractor);
        user = findViewById(R.id.user);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (types == 0){RegisterContractor();}
                else {
                    Register();
                }

            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

        contractor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    user.setChecked(false);
                    aditional_linear.setVisibility(View.VISIBLE);
                    types = 0;
                }
            }
        });

        user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    contractor.setChecked(false);
                    aditional_linear.setVisibility(View.GONE);
                    types = 1;
                }
            }
        });

    }

    private void RegisterContractor() {
        String rname = name.getText().toString();
        String remail = email.getText().toString().trim();
        String rpassword = password.getText().toString().trim();
        String rcpassword = cpassword.getText().toString().trim();
        //String apgender = aprgender.getText().toString().trim();
        String rphoneno = phoneno.getText().toString().trim();
        String rcname = companyname.getText().toString().trim();
        String rcfound = companyfound.getText().toString().trim();
        String rcbuilt = built.getText().toString().trim();

        if (TextUtils.isEmpty(rname)){
            name.setError("Name Required");
            return;
        }
        if (TextUtils.isEmpty(rpassword)){
            password.setError("password required");
            return;
        }
        if (!rpassword.equals(rcpassword)){
            cpassword.setError("password does not same");
            return;
        }
        if (TextUtils.isEmpty(rphoneno)){
            phoneno.setError("password required");
            return;
        }if (TextUtils.isEmpty(rcname)){
            Toast.makeText(this, "All Field Required", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(rcfound)){
            Toast.makeText(this, "All Field Required", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(rcbuilt)){
            Toast.makeText(this, "All Field Required", Toast.LENGTH_SHORT).show();
            return;
        }if (rcpassword.length()<8){
            password.setError("password length must be 8 char long");
            return;
        }

        auth.createUserWithEmailAndPassword(remail,rpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String date = DateFormat.getDateInstance().format(new Date());
                String id = authResult.getUser().getUid();



                Data data = new Data(id,rname,remail,rpassword,rphoneno,date,types,rcname,rcfound,rcbuilt);


                firebaseDatabase.getReference().child("User").child(id).setValue(data);
                Toast.makeText(RegisterActivity.this,"Register successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }


    public void Register() {

        String rname = name.getText().toString();
        String remail = email.getText().toString().trim();
        String rpassword = password.getText().toString().trim();
        String rcpassword = cpassword.getText().toString().trim();
        //String apgender = aprgender.getText().toString().trim();
        String rphoneno = phoneno.getText().toString().trim();

        if (TextUtils.isEmpty(rname)){
            name.setError("Name Required");
            return;
        }
        if (TextUtils.isEmpty(rpassword)){
            password.setError("password required");
            return;
        }
        if (!rpassword.equals(rcpassword)){
            cpassword.setError("password does not same");
            return;
        }
        if (TextUtils.isEmpty(rphoneno)){
            phoneno.setError("password required");
            return;
        }if (rcpassword.length()<8){
            password.setError("password length must be 8 char long");
            return;
        }
        auth.createUserWithEmailAndPassword(remail,rpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String date = DateFormat.getDateInstance().format(new Date());
                String id = authResult.getUser().getUid();



                Data data = new Data(id,rname,remail,rpassword,rphoneno,date,types);


                firebaseDatabase.getReference().child("User").child(id).setValue(data);
                Toast.makeText(RegisterActivity.this,"Register successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }
}