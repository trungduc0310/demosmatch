package com.example.phamtrungduc.smatch.activity;



import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.servicenotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity {

    private ViewPager mviewPager;
    public static TextView tv_thongbao;
    private TabLayout mTablayout;
    private Toolbar toolbar;
    //CounterFab counterFab;


    ImageButton img_find;
    private int[] tabIcon={R.drawable.ic_home_black_24dp,R.drawable.ic_phone_black_24dp,R.drawable.ic_notifications_black_24dp,R.drawable.ic_menu_black_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        Anhxa();
        Kiemtradangnhap();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Kiemtraketnoi();
        CustomActionBar();
        Reconnect();

    }

    private void Reconnect() {
        mviewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kiemtraketnoi();
            }
        });
    }

    private void Kiemtraketnoi() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            tv_thongbao.setVisibility(View.GONE);
//            Toast.makeText(this, "Không có kết nối", Toast.LENGTH_SHORT).show();
        }else{
            tv_thongbao.setVisibility(View.VISIBLE);
        }

    }

    private void CustomActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionbar_view=inflater.inflate(R.layout.custom_actionbar_trangchu,null);
        getSupportActionBar().setCustomView(actionbar_view);
    }


    private void Kiemtradangnhap() {

        if (MyFirebaseMessagingService.mAuth.getCurrentUser()==null){
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        }

    }
    private void Anhxa() {
        tv_thongbao=findViewById(R.id.tv_thongbao_internet);
        mviewPager=findViewById(R.id.container);
        mTablayout=findViewById(R.id.tabs);
        img_find=findViewById(R.id.btnimg_timkiem);
        toolbar=findViewById(R.id.trangchu_toolbar);
        MyFirebaseMessagingService.mAuth=FirebaseAuth.getInstance();
        MyFirebaseMessagingService.mUser=MyFirebaseMessagingService.mAuth.getCurrentUser();

        // counterFab=findViewById(R.id.counterFab);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        // counterFab.setCount(5);
        FragmentManager fragmentManager= getSupportFragmentManager();
        PagerAdapter adapter= new com.example.phamtrungduc.smatch.adapter.PagerAdapter(fragmentManager);
        mviewPager.setAdapter(adapter);

        mTablayout.setupWithViewPager(mviewPager);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        mTablayout.setTabsFromPagerAdapter(adapter);
        mTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mviewPager));
        setUpTabicon();

    }

    private void setUpTabicon() {
        for (int i=0;i<4;i++){
            mTablayout.getTabAt(i).setIcon(tabIcon[i]);
        }
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
                startActivity(new Intent(HomeActivity.this,SearchActivity.class));
                return true;
        }
        return false;
    }
}
