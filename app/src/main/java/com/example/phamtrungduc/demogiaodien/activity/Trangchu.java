package com.example.phamtrungduc.demogiaodien.activity;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class Trangchu extends AppCompatActivity {

    ViewPager mviewPager;
    TabLayout mTablayout;
    //CounterFab counterFab;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        ActionBar actionBar=getSupportActionBar();
        //actionBar.hide();
        Kiemtradangnhap();
        Anhxa();

    }

    private void Kiemtradangnhap() {
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        if (mAuth.getCurrentUser()==null){
            startActivity(new Intent(Trangchu.this,Dangnhap.class));
        }
    }
    private void Anhxa() {
        mviewPager=findViewById(R.id.container);
        mTablayout=findViewById(R.id.tabs);
       // counterFab=findViewById(R.id.counterFab);

       // counterFab.setCount(5);
        FragmentManager fragmentManager= getSupportFragmentManager();
        PagerAdapter adapter= new com.example.phamtrungduc.demogiaodien.adapter.PagerAdapter(fragmentManager);
        mviewPager.setAdapter(adapter);

        mTablayout.setupWithViewPager(mviewPager);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        mTablayout.setTabsFromPagerAdapter(adapter);
        mTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mviewPager));


    }


}
