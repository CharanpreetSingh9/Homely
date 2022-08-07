package com.example.homely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText emailid;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();


        emailid = findViewById(R.id.et_reset);
        btn_reset = (Button) findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });



    }

    public void reset() {
        String email = emailid.getText().toString().trim();
        if(email.isEmpty()){
            emailid.setError("Email id required");
            emailid.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,"Check the Email reset the password",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ResetPasswordActivity.this,"email id is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}