package com.example.phamtrungduc.demogiaodien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phamtrungduc.demogiaodien.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Thongbao extends Fragment {
    public Thongbao() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongbao,container,false);
    }
}
