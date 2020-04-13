package com.example.phamtrungduc.demogiaodien.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.Retrofit2.APIUntils;
import com.example.phamtrungduc.demogiaodien.Retrofit2.DataClient;
import com.example.phamtrungduc.demogiaodien.activity.Dangnhap;
import com.example.phamtrungduc.demogiaodien.activity.Thembaiviet;
import com.example.phamtrungduc.demogiaodien.activity.Trangchu;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBangtin;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.example.phamtrungduc.demogiaodien.entity.Nguoidung;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Bangtin extends Fragment {
    ImageView imgavtuser;
    EditText edtdangbai;
    ImageButton ibtndangbai;
    ListView lvbangtin;
    LinearLayout ln_status;
    public List<Baiviet> dsbaiviet;
    AdapterBangtin adapter;
    View footerView;
    mHander hander;
    boolean limitdata=false;
    boolean loading=false;
    int page=1;

    public Bangtin() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bangtin, container, false);
        AnhXa(view);
        EventClick();
        LoadDataUser();
        LoadDataNewFeed(page);
        LoadMoreData();
        return view;

    }

    private void LoadMoreData() {
        lvbangtin.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&loading==false&&limitdata==false){
                    loading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void LoadDataUser() {
        try {
            Picasso.with(getContext()).load(String.valueOf(Trangchu.mUser.getPhotoUrl()))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(imgavtuser);
//            Log.d("anhdaidienphotoUri", String.valueOf(Trangchu.mUser.getPhotoUrl()));
        }catch (NullPointerException nex){

        }

    }

    private void LoadDataNewFeed(int page) {
        DataClient dataClient=APIUntils.getData();
        Call<List<Baiviet>> callback=dataClient.nguoidung_getbaiviet(page);
        callback.enqueue(new Callback<List<Baiviet>>() {
            @Override
            public void onResponse(Call<List<Baiviet>> call, Response<List<Baiviet>> response) {
                try{
                    List<Baiviet> baiviet1=response.body();
                    if (response!=null && response.body().size()>0){
                        lvbangtin.removeFooterView(footerView);
                        for (int i=0;i<baiviet1.size();i++){
                            dsbaiviet.add(new Baiviet(baiviet1.get(i).getIdbaiviet(), baiviet1.get(i).getEmailnguoidung(), baiviet1.get(i).getTennguoidung()
                                    , baiviet1.get(i).getNoidung(), baiviet1.get(i).getThoigian(), baiviet1.get(i).getTieude(), baiviet1.get(i).getHinhanh()
                                    , baiviet1.get(i).getAnhdaidien()));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }catch (NullPointerException nex){
                    limitdata=true;
                    lvbangtin.removeFooterView(footerView);
//                    Toast.makeText(getContext(), "Đã hết bài viết", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Baiviet>> call, Throwable t) {
                    Log.d("dsbaiviet", t.getMessage());

            }
        });
    }

    private void EventClick() {
        edtdangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Thembaiviet.class);
                startActivity(intent);
            }
        });
        ibtndangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Thembaiviet.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa(View view) {
        imgavtuser = view.findViewById(R.id.img_bangtin_useravt);
        edtdangbai = view.findViewById(R.id.edt_bangtin_dangbai);
        ibtndangbai = view.findViewById(R.id.btnimg_bangtin_themhinhanh);
        lvbangtin = view.findViewById(R.id.lv_dsbangtin);
        ln_status=view.findViewById(R.id.ln_bangtin_status);

        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.processbar,null);
        edtdangbai.setFocusable(false);
        dsbaiviet= new ArrayList<>();
        adapter = new AdapterBangtin(getContext(), R.layout.item_baiviet_pagefriend, dsbaiviet);
        hander= new mHander();
        lvbangtin.setAdapter(adapter);

    }
    class mHander extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvbangtin.addFooterView(footerView);
                    break;
                case 1:
                    LoadDataNewFeed(++page);
                    loading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            hander.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=hander.obtainMessage(1);
            hander.sendMessage(message);
            super.run();
        }
    }


}
