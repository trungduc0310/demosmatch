package com.example.phamtrungduc.demogiaodien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.activity.ThongtinSanbong;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterDanhsachsanbong;
import com.example.phamtrungduc.demogiaodien.entity.Sanbong;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Danhsachsanbong extends Fragment {
    List<Sanbong> dssanbong=new ArrayList<>();
    List<String> dskhuvuc= new ArrayList<>();
    ListView lvdssanbong;
    AdapterDanhsachsanbong adapter;
    ArrayAdapter<String> adapterkhuvuc;
    Button btncall;
    Spinner spinnerKhuvuc;

    public Danhsachsanbong() {
    }
    public void fakedata1(){
        dssanbong.add(new Sanbong("Thiên Trường 1","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 2","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 3","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 4","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
        dssanbong.add(new Sanbong("Thiên Trường 5","Nam Định","0966541248","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/man-icon.png"));
    }
    public void fakedata2(){
        dskhuvuc.add("Hoàng Mai");
        dskhuvuc.add("Hai Bà Trưng");
        dskhuvuc.add("Hà Đông");
        dskhuvuc.add("Cầu Giấy");
        dskhuvuc.add("Thanh Xuân");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dssanbong,container,false);
        lvdssanbong=view.findViewById(R.id.lv_dssanbong);
        spinnerKhuvuc=view.findViewById(R.id.spin_dskhuvuc);
        fakedata1();
        fakedata2();
        adapter=new AdapterDanhsachsanbong(getContext(),R.layout.item_fragment_dssanbong,dssanbong);
        lvdssanbong.setAdapter(adapter);
        lvdssanbong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(),ThongtinSanbong.class));
            }
        });

        adapterkhuvuc= new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dskhuvuc);
        adapterkhuvuc.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerKhuvuc.setAdapter(adapterkhuvuc);

        return view;

    }
}
