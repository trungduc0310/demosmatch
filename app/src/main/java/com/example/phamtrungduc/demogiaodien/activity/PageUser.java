package com.example.phamtrungduc.demogiaodien.activity;

import android.app.ActionBar;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBangtin;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class PageUser extends AppCompatActivity {
    TextView tvusername;
    CircleImageView imgavt;
    String username;
    String anhdaidien;
    int id_nguoidung_baiviet,id_nguoidung;
    ImageView img_back;
    ImageButton imgbtn_edit_profile;
    AdapterBangtin adapterBangtin;
    ListView lv_dsbaidang;
    List<Baiviet> dsbaiviet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageuser);
        AnhXa();
        getProfile();
        EventClick();
        adapterBangtin=new AdapterBangtin(PageUser.this,R.layout.item_baiviet_pagefriend,dsbaiviet);
        lv_dsbaidang.setAdapter(adapterBangtin);

    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgbtn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageUser.this,Thongtinnguoidung.class));
            }
        });
    }

    private void getProfile() {
        if(getIntent().getSerializableExtra("thongtinbaiviet")!=null){
            Baiviet baiviet= (Baiviet) getIntent().getSerializableExtra("thongtinbaiviet");
//        id_nguoidung_baiviet=Integer.parseInt(baiviet.getIdnguoidung());
//        if (id_nguoidung_baiviet==id_nguoidung){
//            imgbtn_edit_profile.setVisibility(View.VISIBLE);
//        }else{
//            imgbtn_edit_profile.setVisibility(View.INVISIBLE);
//        }
            username=baiviet.getTennguoidung();
            anhdaidien=baiviet.getAnhdaidien();
            tvusername.setText(username);
            Picasso.with(PageUser.this).load(anhdaidien)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(imgavt);
            if (Trangchu.mUser.getEmail().equals(baiviet.getEmailnguoidung())){
                imgbtn_edit_profile.setVisibility(View.VISIBLE);
            }else{
                imgbtn_edit_profile.setVisibility(View.GONE);
            }
        }else{
            username=Trangchu.mUser.getDisplayName();
            anhdaidien= String.valueOf(Trangchu.mUser.getPhotoUrl());
            tvusername.setText(username);
            Picasso.with(PageUser.this).load(anhdaidien)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(imgavt);
            imgbtn_edit_profile.setVisibility(View.VISIBLE);
        }



    }


    private void AnhXa() {
        tvusername=findViewById(R.id.tv_pageuser_username);
        imgavt=findViewById(R.id.img_pageuser_hinhanh);
        lv_dsbaidang=findViewById(R.id.lv_pageuser_dsbaiviet);
        imgbtn_edit_profile=findViewById(R.id.imgbtn_pageusser_updateusername);
        img_back=findViewById(R.id._img_pageuser_back);
        dsbaiviet= new ArrayList<>();

    }


}
