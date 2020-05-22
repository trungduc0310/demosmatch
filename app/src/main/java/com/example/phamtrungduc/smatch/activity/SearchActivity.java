package com.example.phamtrungduc.smatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.adapter.NewFeedAdapter;
import com.example.phamtrungduc.smatch.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText edt_timkiem;
    private ImageButton img_search;
    private ProgressBar pro_timkiem;
    private ListView lv_kqtimkiem;
    private TextView tv_thongbao;
    private NewFeedAdapter adapter;
    private List<Post> mlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);
        AnhXa();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventClick();
    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edt_timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String timkiem=edt_timkiem.getText().toString();
                if (timkiem.length()>0){
                    pro_timkiem.setVisibility(View.VISIBLE);
                    getDL(timkiem);
                }
                else{
                    tv_thongbao.setVisibility(View.GONE);
                    pro_timkiem.setVisibility(View.GONE);
                    mlist=new ArrayList<>();
                    adapter= new NewFeedAdapter(SearchActivity.this,R.layout.item_baiviet_pagefriend,mlist);
                    lv_kqtimkiem.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDL(final String timkiem) {
        DataClient dataClient= APIUntils.getData();
        final Call<List<Post>> callback=dataClient.nguoidung_timkiem(timkiem);
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        try{
                            tv_thongbao.setVisibility(View.GONE);
                            pro_timkiem.setVisibility(View.GONE);
                            Log.d("timkiem", response.body().get(0).getIdbaiviet());
                            mlist=response.body();
                            lv_kqtimkiem.post(new Runnable() {
                                @Override
                                public void run() {
                                    adapter= new NewFeedAdapter(SearchActivity.this,R.layout.item_baiviet_pagefriend,mlist);
                                    lv_kqtimkiem.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }catch (NullPointerException nullex){
                            pro_timkiem.setVisibility(View.GONE);
                            tv_thongbao.setVisibility(View.VISIBLE);
                            tv_thongbao.setText("Không có kết quả nào phù hợp với: '"+timkiem+"'");
                            mlist=new ArrayList<>();
                            lv_kqtimkiem.post(new Runnable() {
                                @Override
                                public void run() {
                                    adapter= new NewFeedAdapter(SearchActivity.this,R.layout.item_baiviet_pagefriend,mlist);
                                    lv_kqtimkiem.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Log.d("timkiemfail", t.getMessage());
                    }
                });
            }
        }).start();
    }


    private void AnhXa() {
        edt_timkiem=findViewById(R.id.edt_timkiem_timkiem);
        img_back=findViewById(R.id._img_timkiem_back);
        img_search=findViewById(R.id.btnimg_timkiem);
        pro_timkiem=findViewById(R.id.progress_timkiem);
        lv_kqtimkiem=findViewById(R.id.lv_timkiem);
        tv_thongbao=findViewById(R.id.tv_timkiem_thongbao);
        tv_thongbao.setVisibility(View.INVISIBLE);
        mlist= new ArrayList<>();
        pro_timkiem.setVisibility(View.INVISIBLE);

    }
}
