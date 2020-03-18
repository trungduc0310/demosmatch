package com.example.phamtrungduc.demogiaodien.activity;

import android.app.ActionBar;
import android.content.Intent;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBangtin;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PageUser extends AppCompatActivity {
    TextView tvusername;
    ImageView imgavt;
    String username;
    String anhdaidien;
    AdapterBangtin adapterBangtin;
    ListView lv_dsbaidang;
    List<Baiviet> dsbaiviet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageuser);
        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        AnhXa();
        getTrangcanhan();
        fakeData();
        adapterBangtin=new AdapterBangtin(PageUser.this,R.layout.item_baiviet_pagefriend,dsbaiviet);
        lv_dsbaidang.setAdapter(adapterBangtin);

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
        lv_dsbaidang=findViewById(R.id.lv_pageuser_dsbaiviet);
        dsbaiviet= new ArrayList<>();

    }

    private void fakeData() {
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người2"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người1"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người4"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người5"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người6"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người7"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người8"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png",username,"26/08/2019","Tiêu đề","Xin chào mọi người9"));
    }
}
