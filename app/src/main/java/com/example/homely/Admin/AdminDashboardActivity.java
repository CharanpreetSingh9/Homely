package com.example.homely.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.homely.Adapter.PostProjectAdapter;
import com.example.homely.MainActivity;
import com.example.homely.Model.PostProject;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AdminDashboardActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getSupportActionBar().setTitle("Admin");





    }

    public void AdminContractor(View view) {
        startActivity(new Intent(getApplicationContext(), AdminContractorActivity.class));
    }

    public void AdminUser(View view) {
        startActivity(new Intent(getApplicationContext(), AdminUserActivity.class));

    }

    public void AdminPost(View view) {
        startActivity(new Intent(getApplicationContext(), AdminPostActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        MenuItem logout = menu.findItem(R.id.lagout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                finish();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}