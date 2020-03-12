package com.example.phamtrungduc.demogiaodien.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterDanhsachsanbong;
import com.example.phamtrungduc.demogiaodien.entity.Sanbong;

import java.util.ArrayList;
import java.util.List;

public class Danhsachsanbong extends Fragment {
    List<Sanbong> dssanbong=new ArrayList<>();
    ListView lvdssanbong;
    AdapterDanhsachsanbong adapter;
    Button btncall;
    public Danhsachsanbong() {
    }
    public void fakedata(){
        dssanbong.add(new Sanbong("Thiên Trường 1","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 2","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 3","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 4","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dssanbong,container,false);
        lvdssanbong=view.findViewById(R.id.lv_dssanbong);

        fakedata();

        adapter=new AdapterDanhsachsanbong(getContext(),R.layout.item_fragment_dssanbong,dssanbong);
        lvdssanbong.setAdapter(adapter);
        return view;

    }
}
