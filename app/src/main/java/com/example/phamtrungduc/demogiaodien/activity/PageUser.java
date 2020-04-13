package com.example.phamtrungduc.demogiaodien.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.Retrofit2.APIUntils;
import com.example.phamtrungduc.demogiaodien.Retrofit2.DataClient;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBangtin;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBinhluan;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.example.phamtrungduc.demogiaodien.fragment.Bangtin;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageUser extends AppCompatActivity {
    TextView tvusername,tv_thongbao;
    CircleImageView imgavt;
    ProgressBar progressBar;
    ImageView img_back;
    ImageButton imgbtn_edit_profile;
    AdapterBangtin adapter;
    ListView lv_dsbaidang;
    List<Baiviet> dsbaiviet;
    EditText edt_timkiem;
    String username;
    String anhdaidien;
    View footerView;
    mHander hander;
    boolean limitdata=false;
    boolean loading=false;
    int page=1;
    String email_nguoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageuser);
        AnhXa();
        getProfile();
        getPost(email_nguoidung,page);
        loadMoreData();
        EventClick();
    }

    private void loadMoreData() {
        lv_dsbaidang.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void getPost(String email_nguoidung, int page) {
        progressBar.setVisibility(View.VISIBLE);
        DataClient dataClient=APIUntils.getData();
        Call<List<Baiviet>> callback=dataClient.nguoidung_xemtrangcanhan(email_nguoidung,page);
        callback.enqueue(new Callback<List<Baiviet>>() {
            @Override
            public void onResponse(Call<List<Baiviet>> call, Response<List<Baiviet>> response) {
                try{
                    progressBar.setVisibility(View.GONE);
                    List<Baiviet> baiviet1=response.body();
                    if (response!=null && response.body().size()>0){
                        lv_dsbaidang.removeFooterView(footerView);
                        for (int i=0;i<baiviet1.size();i++){
                            dsbaiviet.add(new Baiviet(baiviet1.get(i).getIdbaiviet(), baiviet1.get(i).getEmailnguoidung(), baiviet1.get(i).getTennguoidung()
                                    , baiviet1.get(i).getNoidung(), baiviet1.get(i).getThoigian(), baiviet1.get(i).getTieude(), baiviet1.get(i).getHinhanh()
                                    , baiviet1.get(i).getAnhdaidien()));
                        }
                        adapter.notifyDataSetChanged();
                        CustomListView(lv_dsbaidang);
                    }
                }catch (NullPointerException nex){
                    limitdata=true;
                    lv_dsbaidang.removeFooterView(footerView);
//                    Toast.makeText(PageUser.this, "Đã hết bài viết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Baiviet>> call, Throwable t) {
                try {
                    tv_thongbao.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    dsbaiviet = new ArrayList<>();
                    adapter = new AdapterBangtin(PageUser.this, R.layout.item_baiviet_pagefriend,dsbaiviet);
                    adapter.notifyDataSetChanged();
                    lv_dsbaidang.setAdapter(adapter);
                    CustomListView(lv_dsbaidang);

                }catch (NullPointerException nulle){

                }
            }
        });
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
        edt_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageUser.this,Timkiem.class));
            }
        });

//        edt_timkiem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
    }

    private void getProfile() {
        if(getIntent().getSerializableExtra("thongtinbaiviet")!=null){
            Baiviet baiviet= (Baiviet) getIntent().getSerializableExtra("thongtinbaiviet");
            email_nguoidung=baiviet.getEmailnguoidung();
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
            email_nguoidung=Trangchu.mUser.getEmail();
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
        progressBar=findViewById(R.id.progress_pageuser);
        tv_thongbao=findViewById(R.id.tv_pageusser_thongbao);
        tv_thongbao.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        edt_timkiem=findViewById(R.id.edt_pageuser_timkiem);
//        edt_timkiem.setFocusable(false);
        hideKeyboard(edt_timkiem);
        LayoutInflater inflater= (LayoutInflater) getApplicationContext().getSystemService(PageUser.LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.processbar,null);
        dsbaiviet= new ArrayList<>();
        adapter= new AdapterBangtin(this,R.layout.item_baiviet_pagefriend,dsbaiviet);
        hander= new mHander();
        lv_dsbaidang.setAdapter(adapter);


    }
    private void CustomListView(ListView lv_dsbv) {
        ListAdapter listAdapter = lv_dsbv.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = lv_dsbv.getPaddingTop() + lv_dsbv.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, lv_dsbv);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv_dsbv.getLayoutParams();
        params.height = totalHeight + (lv_dsbv.getDividerHeight() * (listAdapter.getCount() - 1));
        lv_dsbv.setLayoutParams(params);
    }

    public void hideKeyboard(EditText edt) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getApplicationWindowToken(), 0);
    }

    class mHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lv_dsbaidang.addFooterView(footerView);
                    break;
                case 1:
                    getPost(email_nguoidung,++page);
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
