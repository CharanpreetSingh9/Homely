package com.example.homely.Admin;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homely.Login;
import com.example.homely.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashBoard extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        getSupportActionBar().setTitle("Admin Dashboard");
    }


    public void AdminContractor(View view) {
        startActivity(new Intent(getApplicationContext(), AdminContractors.class));
    }

    public void AdminRecruiter(View view) {
        startActivity(new Intent(getApplicationContext(), AdminUsers.class));

    }

    public void AdminJobPost(View view) {
        startActivity(new Intent(getApplicationContext(), AdminPostWork.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        MenuItem logout = menu.findItem(R.id.lagout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
