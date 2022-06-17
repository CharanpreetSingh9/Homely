package com.example.homely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    Animation top,bottom;
    Boolean check=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        top = AnimationUtils.loadAnimation(this, R.anim.topanimation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottomanimation);
        ImageView logo=findViewById(R.id.logo);
        TextView slogan=findViewById(R.id.slogan);
        logo.setAnimation(top);
        slogan.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!check){

                    Intent i = new Intent(SplashScreen.this,Login.class);
                    startActivity(i);
                    finish();

                }
            }
        },3000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        check=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(check){
            top = AnimationUtils.loadAnimation(this, R.anim.topanimation);
            bottom = AnimationUtils.loadAnimation(this, R.anim.bottomanimation);
            ImageView logo=findViewById(R.id.logo);
            TextView slogan=findViewById(R.id.slogan);
//Set animation to elements

            logo.setAnimation(top);
            slogan.setAnimation(bottom);

            Intent i = new Intent(SplashScreen.this,Login.class);
            startActivity(i);
            finish();


        }
    }
}