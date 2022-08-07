package com.example.homely.Contractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homely.LoginActivity;
import com.example.homely.Model.Global;
import com.example.homely.Model.PostProject;
import com.example.homely.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private DatabaseReference mJobPost;
    private int mYear, mMonth, mDay;

    final Calendar myCalendar= Calendar.getInstance();

    EditText post_name , post_area,post_budget,post_location;
    TextView post_date;
    CheckBox check_with , check_without;
    Button btn_post;
    String material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setTitle("Post Project");


        post_date = findViewById(R.id.post_date);
        post_name = (EditText) findViewById(R.id.post_projectname);
        post_area = (EditText) findViewById(R.id.post_area);
        post_budget = (EditText) findViewById(R.id.post_budget);
        post_location = (EditText) findViewById(R.id.post_location);
        btn_post = (Button) findViewById(R.id.btn_post);
        check_with = (CheckBox) findViewById(R.id.check_with);
        check_without = (CheckBox) findViewById(R.id.check_without);



        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        post_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PostActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });







        auth = FirebaseAuth.getInstance();
        FirebaseUser mUser = auth.getCurrentUser();
        String uid = mUser.getUid();

        FirebaseDatabase.getInstance().getReference().child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Global.USER_NAME_POST  = snapshot.child("fullname").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        check_with.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    check_without.setChecked(false);
                    material = "with";

                }
            }
        });

        check_without.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    check_with.setChecked(false);
                    material = "without";

                }
            }
        });


        mJobPost = FirebaseDatabase.getInstance().getReference().child("Project Post");

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                insertData();
            }

        });




        }

    private void insertData() {

        String ProjectName = post_name.getText().toString().trim();
        String Area = post_area.getText().toString().trim();
        String Budget= post_budget.getText().toString().trim();
        String Location = post_location.getText().toString().trim();
        String enddate = post_date.getText().toString().trim();



        if (TextUtils.isEmpty(ProjectName)){
            Toast.makeText(PostActivity.this, "Project Name Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Area)){
            Toast.makeText(PostActivity.this, "Area Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Budget)){
            Toast.makeText(PostActivity.this, "Budget Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Location)){
            Toast.makeText(PostActivity.this, "Location Required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(enddate)){
            Toast.makeText(PostActivity.this, "Date Required", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = mJobPost.push().getKey();
        String date = DateFormat.getDateInstance().format(new Date());
        PostProject postProject = new PostProject(ProjectName,Area,Budget,Location,material,auth.getCurrentUser().getUid(),date,id,enddate,Global.USER_NAME_POST);

        mJobPost.child(id).setValue(postProject);
        Toast.makeText(getApplicationContext(),"successfull",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), ContractorDashboardActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        MenuItem logout = menu.findItem(R.id.lagout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void updateLabel(){
        //String myFormat="MM/dd/yy";
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        post_date.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PostActivity.this,ContractorDashboardActivity.class));
    }
}