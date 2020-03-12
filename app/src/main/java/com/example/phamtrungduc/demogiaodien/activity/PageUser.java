package com.example.phamtrungduc.demogiaodien.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.squareup.picasso.Picasso;

public class PageUser extends AppCompatActivity {
    TextView tvusername;
    ImageView imgavt;
    String username;
    String anhdaidien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageuser);
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        AnhXa();
        getTrangcanhan();
    }

    private void getTrangcanhan() {
        Baiviet baiviet= (Baiviet) getIntent().getSerializableExtra("thongtinnguoidung");
        username=baiviet.getUsername();
        anhdaidien=baiviet.getAvt();
        tvusername.setText(username);
        Picasso.with(PageUser.this).load(anhdaidien)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imgavt);
    }


    private void AnhXa() {
        tvusername=findViewById(R.id.tv_pageuser_username);
        imgavt=findViewById(R.id.img_pageuser_hinhanh);
    }
}
