package com.example.phamtrungduc.demogiaodien.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.Retrofit2.APIUntils;
import com.example.phamtrungduc.demogiaodien.Retrofit2.DataClient;
import com.example.phamtrungduc.demogiaodien.activity.ThongtinSanbong;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterDanhsachsanbong;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterKhuvuc;
import com.example.phamtrungduc.demogiaodien.entity.Khuvuc;
import com.example.phamtrungduc.demogiaodien.entity.Sanbong;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Danhsachsanbong extends Fragment {
    List<Sanbong> dssanbong;
    List<Khuvuc> dskhuvuc=new ArrayList<>();
    ListView lvdssanbong;
    AdapterDanhsachsanbong adapter;
    AdapterKhuvuc adapterkhuvuc;
    Spinner spinnerKhuvuc;
    TextView thongbaosanbong;

    int id_spinner;
    int page=1;
    View footerView;
    mHander hander;
    boolean limitdata=false;
    boolean loading=false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dssanbong,container,false);
        Anhxa(view);
        getDataKhuvuc();
        EventSelectItemspiner();
        EventSelectListView();
        LoadMoreData();
        return view;

    }

    private void LoadMoreData() {
        lvdssanbong.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void EventSelectListView() {
        lvdssanbong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getContext(),ThongtinSanbong.class);
                intent.putExtra("id_sanbong",dssanbong.get(position).getIdSanbong());
                intent.putExtra("diachi",dssanbong.get(position).getDiachi());
                intent.putExtra("sodienthoai",dssanbong.get(position).getSodienthoai());
                intent.putExtra("tensanbong",dssanbong.get(position).getTensanbong());
                startActivity(intent);
            }
        });
    }

    private void EventSelectItemspiner() {
        spinnerKhuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_spinner=position;
                getDanhsachsanbong(id_spinner,page);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDanhsachsanbong(int position,int page) {
        DataClient dataClient=APIUntils.getData();
        Call<List<Sanbong>> callback= dataClient.nguoidung_xemdanhsachsanbong(dskhuvuc.get(position).getIdKhuvuc(),page);
        callback.enqueue(new Callback<List<Sanbong>>() {
            @Override
            public void onResponse(Call<List<Sanbong>> call, Response<List<Sanbong>> response) {
                try{
                    List<Sanbong> danhsachsanbong1=response.body();
                    if (response!=null && response.body().size()>0){
                        lvdssanbong.removeFooterView(footerView);
                        for (int i=0;i<danhsachsanbong1.size();i++){
                            dssanbong.add(new Sanbong(danhsachsanbong1.get(i).getIdSanbong(),
                                    danhsachsanbong1.get(i).getTensanbong(),danhsachsanbong1.get(i).getDiachi(),danhsachsanbong1.get(i).getSodienthoai(),
                                    danhsachsanbong1.get(i).getIdKhuvuc()));
                        }
                        thongbaosanbong.setVisibility(View.INVISIBLE);
                        adapter.notifyDataSetChanged();
                    }
                }catch (NullPointerException nulle){
                    limitdata=true;
                    lvdssanbong.removeFooterView(footerView);
//                    thongbaosanbong.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Call<List<Sanbong>> call, Throwable t) {
                Log.d("onFailure_sanbong", t.getMessage());
            }
        });
    }

    private void getDataKhuvuc() {
        DataClient dataClient=APIUntils.getData();
        Call<List<Khuvuc>> callback=dataClient.sanbong_getkhuvuc();
        callback.enqueue(new Callback<List<Khuvuc>>() {
            @Override
            public void onResponse(Call<List<Khuvuc>> call, Response<List<Khuvuc>> response) {
                dskhuvuc=response.body();
                if (dskhuvuc.size()>0){
                    adapterkhuvuc= new AdapterKhuvuc(getContext(),R.layout.item_khuvuc,dskhuvuc);
                    spinnerKhuvuc.setAdapter(adapterkhuvuc);
                }else{
                    Toast.makeText(getContext(), "Không có khu vưc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Khuvuc>> call, Throwable t) {
                Log.d("onFailure khuvuc", t.getMessage());
            }
        });
    }

    private void Anhxa(View view) {
        lvdssanbong=view.findViewById(R.id.lv_dssanbong);
        spinnerKhuvuc=view.findViewById(R.id.spin_dskhuvuc);
        thongbaosanbong=view.findViewById(R.id.tv_danhsachsanbong_thongbao);
        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.processbar,null);
        dssanbong= new ArrayList<>();
        adapter = new AdapterDanhsachsanbong(getContext(), R.layout.item_fragment_dssanbong, dssanbong);
        lvdssanbong.setAdapter(adapter);

    }
    class mHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvdssanbong.addFooterView(footerView);
                    break;
                case 1:
                    getDanhsachsanbong(id_spinner,++page);
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
