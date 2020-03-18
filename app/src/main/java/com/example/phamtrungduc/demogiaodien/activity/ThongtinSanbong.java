package com.example.phamtrungduc.demogiaodien.activity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ThongtinSanbong  extends AppCompatActivity {
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thongtinsanbong);
        super.onCreate(savedInstanceState);
        ActionBar actionBar=  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ratingBar=findViewById(R.id.rating_thongtinsanbong_danhgia);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(ThongtinSanbong.this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
