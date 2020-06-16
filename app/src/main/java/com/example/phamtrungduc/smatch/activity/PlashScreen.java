package com.example.phamtrungduc.smatch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.phamtrungduc.smatch.R;

public class PlashScreen extends AppCompatActivity {
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plash_screen);
        relativeLayout=findViewById(R.id.relative);
        Animation a= AnimationUtils.loadAnimation(this,R.anim.animaionplash);
        relativeLayout.startAnimation(a);
        Thread timer=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(PlashScreen.this,HomeActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }
}