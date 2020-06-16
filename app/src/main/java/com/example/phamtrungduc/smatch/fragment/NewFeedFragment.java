package com.example.phamtrungduc.smatch.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.servicenotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.activity.AddPostActivity;
import com.example.phamtrungduc.smatch.activity.HomeActivity;
import com.example.phamtrungduc.smatch.adapter.NewFeedAdapter;
import com.example.phamtrungduc.smatch.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewFeedFragment extends Fragment {
    private ProgressBar pro_bangtin;
    private ImageView imgavtuser;
    private EditText edtdangbai;
    private ImageButton ibtndangbai;
    private ListView lvbangtin;
    private LinearLayout ln_status;
    private List<Post> dsbaiviet;
    private NewFeedAdapter adapter;
    private View footerView;
    private mHander hander;
    private boolean limitdata = false;
    private boolean loading = false;
    private int page = 1;

    public NewFeedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bangtin, container, false);
        AnhXa(view);
        LoadDataUser();
        LoadDataNewFeed(page);
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        EventClick();
        LoadMoreData();
    }

    private void LoadMoreData() {
        lvbangtin.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lvbangtin.getLastVisiblePosition() - lvbangtin.getHeaderViewsCount() -
                        lvbangtin.getFooterViewsCount()) >= (adapter.getCount() - 1)) {
                    if (loading == false && limitdata == false) {
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

    private void LoadDataUser() {
        try {
            Picasso.with(getContext()).load(String.valueOf(MyFirebaseMessagingService.mUser.getPhotoUrl()))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(imgavtuser);
        } catch (NullPointerException nex) {

        }

    }

    private void LoadDataNewFeed(int page) {
        DataClient dataClient = APIUntils.getData();
        final Call<List<Post>> callback = dataClient.nguoidung_getbaiviet(page);
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        try {
                            HomeActivity.tv_thongbao.setVisibility(View.GONE);
                            pro_bangtin.setVisibility(View.GONE);
                            List<Post> post1 = response.body();
                            if (response != null && response.body().size() > 0) {
                                lvbangtin.removeFooterView(footerView);
                                for (int i = 0; i < post1.size(); i++) {
                                    dsbaiviet.add(new Post(post1.get(i).getIdbaiviet(), post1.get(i).getEmailnguoidung(), post1.get(i).getTennguoidung()
                                            , post1.get(i).getNoidung(), post1.get(i).getThoigian(), post1.get(i).getTieude(), post1.get(i).getHinhanh()
                                            , post1.get(i).getAnhdaidien()));
                                }
                                lvbangtin.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } catch (NullPointerException nex) {
                            limitdata = true;
                            lvbangtin.removeFooterView(footerView);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Log.d("dsbaiviet", t.getMessage());

                    }
                });
            }
        }).start();

    }

    private void EventClick() {
        edtdangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPostActivity.class);
                startActivity(intent);
            }
        });
        ibtndangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPostActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa(View view) {
        imgavtuser = view.findViewById(R.id.img_bangtin_useravt);
        edtdangbai = view.findViewById(R.id.edt_bangtin_dangbai);
        ibtndangbai = view.findViewById(R.id.btnimg_bangtin_themhinhanh);
        lvbangtin = view.findViewById(R.id.lv_dsbangtin);
        ln_status = view.findViewById(R.id.ln_bangtin_status);
        pro_bangtin = view.findViewById(R.id.progress_bangtin);
        pro_bangtin.setVisibility(View.VISIBLE);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.processbar, null);
        edtdangbai.setFocusable(false);
        dsbaiviet = new ArrayList<>();
        adapter = new NewFeedAdapter(getContext(), R.layout.item_baiviet_pagefriend, dsbaiviet);
        hander = new mHander();
        lvbangtin.setAdapter(adapter);
    }

    class mHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lvbangtin.addFooterView(footerView);
                    break;
                case 1:
                    LoadDataNewFeed(++page);
                    loading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            hander.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = hander.obtainMessage(1);
            hander.sendMessage(message);
            super.run();
        }
    }


}
