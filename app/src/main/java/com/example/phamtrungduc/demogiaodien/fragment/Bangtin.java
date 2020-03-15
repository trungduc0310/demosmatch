package com.example.phamtrungduc.demogiaodien.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;


import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.activity.Thembaiviet;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBangtin;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


public class Bangtin extends Fragment {
    ImageView imgavtuser;
    EditText edtdangbai;
    ImageButton ibtndangbai;
    ListView lvbangtin;
    public static List<Baiviet> dsbaiviet=new ArrayList<>();
    AdapterBangtin adapter;
    public Bangtin() {
    }
    public void fakedata(){
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Thanh Tùng","26/08/2019","Tiêu đề","Xin chào mọi người"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Trung Đức","26/08/2019","Tiêu đề","Xin chào mọi người2"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Trung Hiếu","26/08/2019","Tiêu đề","Xin chào mọi người1"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Trần Quân","26/08/2019","Tiêu đề","Xin chào mọi người4"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Lê Phú","26/08/2019","Tiêu đề","Xin chào mọi người5"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Bảo Thanh","26/08/2019","Tiêu đề","Xin chào mọi người6"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Như Quỳnh","26/08/2019","Tiêu đề","Xin chào mọi người7"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Quang Vinh","26/08/2019","Tiêu đề","Xin chào mọi người8"));
        dsbaiviet.add(new Baiviet("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png","Thủy Tiên","26/08/2019","Tiêu đề","Xin chào mọi người9"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bangtin,container,false);
        imgavtuser=view.findViewById(R.id.img_bangtin_useravt);
        edtdangbai=view.findViewById(R.id.edt_bangtin_dangbai);
        ibtndangbai=view.findViewById(R.id.btnimg_bangtin_themhinhanh);
        lvbangtin=view.findViewById(R.id.lv_dsbangtin);
        fakedata();
        adapter= new AdapterBangtin(getContext(),R.layout.item_baiviet_pagefriend,dsbaiviet);
        lvbangtin.setAdapter(adapter);
        edtdangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),Thembaiviet.class);
                startActivity(intent);

            }
        });
        ibtndangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),Thembaiviet.class);
                startActivity(intent);
            }
        });

        return view;

    }




}
