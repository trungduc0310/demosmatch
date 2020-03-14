package com.example.phamtrungduc.demogiaodien.activity;

import android.os.Bundle;

import android.view.MenuItem;

import com.example.phamtrungduc.demogiaodien.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Thembaiviet extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dangbaiviet);
        super.onCreate(savedInstanceState);
        ActionBar actionBar=  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
