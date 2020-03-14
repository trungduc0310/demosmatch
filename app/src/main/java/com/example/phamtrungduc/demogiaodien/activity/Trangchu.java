package com.example.phamtrungduc.demogiaodien.activity;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class Trangchu extends AppCompatActivity {

    ViewPager mviewPager;
    TabLayout mTablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        Anhxa();

    }

    private void Anhxa() {
        mviewPager=findViewById(R.id.container);
        mTablayout=findViewById(R.id.tabs);
        FragmentManager fragmentManager= getSupportFragmentManager();
        PagerAdapter adapter= new com.example.phamtrungduc.demogiaodien.adapter.PagerAdapter(fragmentManager);
        mviewPager.setAdapter(adapter);

        mTablayout.setupWithViewPager(mviewPager);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        mTablayout.setTabsFromPagerAdapter(adapter);
        mTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mviewPager));
    }


}
