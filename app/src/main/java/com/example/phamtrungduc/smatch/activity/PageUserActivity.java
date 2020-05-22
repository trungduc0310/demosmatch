package com.example.phamtrungduc.smatch.activity;

import android.app.Activity;
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

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.ServiceNotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.adapter.NewFeedAdapter;
import com.example.phamtrungduc.smatch.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageUserActivity extends AppCompatActivity {
    private TextView tvusername,tv_thongbao;
    private CircleImageView imgavt;
    private ProgressBar progressBar;
    private ImageView img_back;
    private ImageButton imgbtn_edit_profile;
    private NewFeedAdapter adapter;
    private ListView lv_dsbaidang;
    private List<Post> dsbaiviet;
    private EditText edt_timkiem;
    private String username="",anhdaidien="";
    private View footerView;
    private mHander hander;
    private boolean limitdata=false;
    private boolean loading=false;
    private int page=1;
    private String email_nguoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageuser);
        AnhXa();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getProfile();
        getPost(email_nguoidung,page);
        loadMoreData();
        EventClick();
    }

    private void loadMoreData() {
        lv_dsbaidang.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lv_dsbaidang.getLastVisiblePosition() - lv_dsbaidang.getHeaderViewsCount() -
                        lv_dsbaidang.getFooterViewsCount()) >= (adapter.getCount() - 1)){
                    if (loading==false&&limitdata==false){
                        loading = true;
                        ThreadData threadData = new ThreadData();
                        threadData.start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void getPost(String email_nguoidung, int page) {
        progressBar.setVisibility(View.VISIBLE);
        DataClient dataClient=APIUntils.getData();
        Call<List<Post>> callback=dataClient.nguoidung_xemtrangcanhan(email_nguoidung,page);
        callback.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                try{
                    progressBar.setVisibility(View.GONE);
                    List<Post> post1 =response.body();
                    if (response!=null && response.body().size()>0){
                        lv_dsbaidang.removeFooterView(footerView);
                        for (int i = 0; i< post1.size(); i++){
                            dsbaiviet.add(new Post(post1.get(i).getIdbaiviet(), post1.get(i).getEmailnguoidung(), post1.get(i).getTennguoidung()
                                    , post1.get(i).getNoidung(), post1.get(i).getThoigian(), post1.get(i).getTieude(), post1.get(i).getHinhanh()
                                    , post1.get(i).getAnhdaidien()));
                        }
                        adapter.notifyDataSetChanged();
                        CustomListView(lv_dsbaidang);
                    }
                }catch (NullPointerException nex){
                    limitdata=true;
                    lv_dsbaidang.removeFooterView(footerView);
//                    Toast.makeText(PageUserActivity.this, "Đã hết bài viết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                try {
                    tv_thongbao.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    dsbaiviet = new ArrayList<>();
                    adapter = new NewFeedAdapter(PageUserActivity.this, R.layout.item_baiviet_pagefriend,dsbaiviet);
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
                onStop();
                onBackPressed();
            }
        });
        imgbtn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageUserActivity.this,UserDetailsActivity.class));
            }
        });
        edt_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageUserActivity.this,SearchActivity.class));
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
            Post post = (Post) getIntent().getSerializableExtra("thongtinbaiviet");
            email_nguoidung= post.getEmailnguoidung();
            username= post.getTennguoidung();
            anhdaidien= post.getAnhdaidien();
            tvusername.setText(username);
            Picasso.with(PageUserActivity.this).load(anhdaidien)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(imgavt);
            if (MyFirebaseMessagingService.mUser.getEmail().equals(post.getEmailnguoidung())){
                imgbtn_edit_profile.setVisibility(View.VISIBLE);
            }else{
                imgbtn_edit_profile.setVisibility(View.GONE);
            }
        }else{
            email_nguoidung=MyFirebaseMessagingService.mUser.getEmail();
            username=MyFirebaseMessagingService.mUser.getDisplayName();
            anhdaidien= String.valueOf(MyFirebaseMessagingService.mUser.getPhotoUrl());
            tvusername.setText(username);
            Picasso.with(PageUserActivity.this).load(anhdaidien)
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
        LayoutInflater inflater= (LayoutInflater) getApplicationContext().getSystemService(PageUserActivity.LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.processbar,null);
        dsbaiviet= new ArrayList<>();
        adapter= new NewFeedAdapter(this,R.layout.item_baiviet_pagefriend,dsbaiviet);
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
