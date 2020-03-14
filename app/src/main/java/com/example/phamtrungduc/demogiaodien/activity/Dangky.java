package com.example.phamtrungduc.demogiaodien.activity;


import android.os.Bundle;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.fragment.Bangtin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class Dangky extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
