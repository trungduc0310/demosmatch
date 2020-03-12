package com.example.phamtrungduc.demogiaodien.activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;

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
