package com.example.phamtrungduc.demogiaodien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterThongbao;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Thongbao extends Fragment {
    ListView lv_dsthongbao;
    AdapterThongbao adapterThongbao;

    public Thongbao() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thongbao,container,false);
        AnhXa(view);
        return view;
    }

    private void AnhXa(View view) {
        lv_dsthongbao=view.findViewById(R.id.lv_dsthongbao);
        adapterThongbao= new AdapterThongbao(getContext(),R.layout.item_fragment_thongbao,Bangtin.dsbaiviet);
        lv_dsthongbao.setAdapter(adapterThongbao);
    }


}
