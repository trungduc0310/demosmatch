package com.example.phamtrungduc.smatch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.activity.MatchDetailsActivity;
import com.example.phamtrungduc.smatch.adapter.MatchAdapter;
import com.example.phamtrungduc.smatch.adapter.AreaAdapter;
import com.example.phamtrungduc.smatch.entity.Area;
import com.example.phamtrungduc.smatch.entity.Match;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchFragment extends Fragment {
    private List<Match> dssanbong;
    private List<Area> dskhuvuc = new ArrayList<>();
    private ListView lvdssanbong;
    private MatchAdapter adapter;
    private AreaAdapter adapterkhuvuc;
    private Spinner spinnerKhuvuc;
    private TextView thongbaosanbong;

    int id_spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dssanbong, container, false);
        Anhxa(view);
        getDataKhuvuc();
        EventSelectItemspiner();
        EventSelectListView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void EventSelectListView() {
        lvdssanbong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MatchDetailsActivity.class);
                intent.putExtra("id_sanbong", dssanbong.get(position).getIdSanbong());
                intent.putExtra("diachi", dssanbong.get(position).getDiachi());
                intent.putExtra("sodienthoai", dssanbong.get(position).getSodienthoai());
                intent.putExtra("tensanbong", dssanbong.get(position).getTensanbong());
                startActivity(intent);
            }
        });
    }

    private void EventSelectItemspiner() {
        spinnerKhuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_spinner = position;
                getDanhsachsanbong(id_spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDanhsachsanbong(int position) {
        DataClient dataClient = APIUntils.getData();
        final Call<List<Match>> callback = dataClient.nguoidung_xemdanhsachsanbong(dskhuvuc.get(position).getIdKhuvuc());
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                        try{
                            thongbaosanbong.setVisibility(View.GONE);
                            List<Match> danhsachsanbong1 = response.body();
                            dssanbong= new ArrayList<>();
                            for (int i = 0; i < danhsachsanbong1.size(); i++) {
                                dssanbong.add(new Match(danhsachsanbong1.get(i).getIdSanbong(),
                                        danhsachsanbong1.get(i).getTensanbong(), danhsachsanbong1.get(i).getDiachi(), danhsachsanbong1.get(i).getSodienthoai(),
                                        danhsachsanbong1.get(i).getIdKhuvuc()));
                            }
                            adapter = new MatchAdapter(getContext(), R.layout.item_fragment_dssanbong, dssanbong);
                            lvdssanbong.post(new Runnable() {
                                @Override
                                public void run() {
                                    lvdssanbong.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }catch (NullPointerException nullex){
                            thongbaosanbong.setVisibility(View.VISIBLE);
                            dssanbong = new ArrayList<>();
                            try{
                                adapter = new MatchAdapter(getContext(), R.layout.item_fragment_dssanbong, dssanbong);
                                lvdssanbong.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        lvdssanbong.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }catch (NullPointerException nullex2){

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Match>> call, Throwable t) {
                        Log.d("onFailure_sanbong", t.getMessage());

                    }
                });
            }
        }).start();

    }

    private void getDataKhuvuc() {
        DataClient dataClient = APIUntils.getData();
        Call<List<Area>> callback = dataClient.sanbong_getkhuvuc();
        callback.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                dskhuvuc = response.body();
                if (dskhuvuc.size() > 0) {
                    try {
                        adapterkhuvuc = new AreaAdapter(getContext(), R.layout.item_khuvuc, dskhuvuc);
                        spinnerKhuvuc.setAdapter(adapterkhuvuc);
                    }catch (NullPointerException nullex){

                    }
                } else {
                    Toast.makeText(getContext(), "Không có khu vưc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Log.d("onFailure khuvuc", t.getMessage());
            }
        });
    }

    private void Anhxa(View view) {
        lvdssanbong = view.findViewById(R.id.lv_dssanbong);
        spinnerKhuvuc = view.findViewById(R.id.spin_dskhuvuc);
        thongbaosanbong = view.findViewById(R.id.tv_danhsachsanbong_thongbao);
        thongbaosanbong.setVisibility(View.INVISIBLE);
        dssanbong = new ArrayList<>();
    }


}
