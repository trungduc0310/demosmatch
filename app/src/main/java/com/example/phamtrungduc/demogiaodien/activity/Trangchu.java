package com.example.phamtrungduc.demogiaodien.activity;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.adapter.PagerAdapter;
import com.example.phamtrungduc.demogiaodien.entity.Nguoidung;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class Trangchu extends AppCompatActivity {

    ViewPager mviewPager;
    TabLayout mTablayout;
    Toolbar toolbar;
    //CounterFab counterFab;
    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;

    ImageButton img_find;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        Kiemtradangnhap();
        Anhxa();
        CustomActionBar();
        EventClick();
    }




    private void CustomActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionbar_view=inflater.inflate(R.layout.custom_actionbar_trangchu,null);
        getSupportActionBar().setCustomView(actionbar_view);
    }

    private void EventClick() {
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
        img_find=findViewById(R.id.btnimg_timkiem);
        toolbar=findViewById(R.id.trangchu_toolbar);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_trangchu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_find:
                startActivity(new Intent(Trangchu.this,Timkiem.class));
                return true;
        }
        return false;
    }
}
