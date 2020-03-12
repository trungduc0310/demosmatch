package com.example.phamtrungduc.demogiaodien.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.phamtrungduc.demogiaodien.fragment.Bangtin;
import com.example.phamtrungduc.demogiaodien.fragment.Danhsachsanbong;
import com.example.phamtrungduc.demogiaodien.fragment.Thongbao;
import com.example.phamtrungduc.demogiaodien.fragment.Tuychon;

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
