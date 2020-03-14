package com.example.phamtrungduc.demogiaodien.adapter;

import com.example.phamtrungduc.demogiaodien.fragment.Bangtin;
import com.example.phamtrungduc.demogiaodien.fragment.Danhsachsanbong;
import com.example.phamtrungduc.demogiaodien.fragment.Thongbao;
import com.example.phamtrungduc.demogiaodien.fragment.Tuychon;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new Bangtin();
                break;
            case 1:
                fragment = new Danhsachsanbong();
                break;
            case 2:
                fragment = new Thongbao();
                break;
            case 3:
                fragment = new Tuychon();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title="Bảng tin";
                break;
            case 1:
                title="Danh sách sân bóng";
                break;
            case 2:
                title="Thông báo";
                break;
            case 3:
                title="Tùy chọn";
                break;
        }
        return title;
    }
}
