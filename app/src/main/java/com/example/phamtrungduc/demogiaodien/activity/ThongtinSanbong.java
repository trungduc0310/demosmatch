package com.example.phamtrungduc.demogiaodien.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.Retrofit2.APIUntils;
import com.example.phamtrungduc.demogiaodien.Retrofit2.DataClient;
import com.example.phamtrungduc.demogiaodien.entity.Chitietsanbong;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongtinSanbong  extends AppCompatActivity {
    RatingBar ratingBar;
    ImageView img_back,img_hinhanh1,img_hinhanh2;
    TextView tv_tensan,tv_loaisan,tv_tenchusan,tv_diachi,tv_sdt,tv_khoanggia;
    String id_sanbong,tensan,diachi,sodienthoai;

    List<Chitietsanbong> chitietsanbong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thongtinsanbong);
        super.onCreate(savedInstanceState);
        AnhXa();
        getDataIntent();
        getData();
        EventClick();
    }

    private void getData() {
        DataClient dataClient=APIUntils.getData();
        Call<List<Chitietsanbong>> callback=dataClient.sanbong_getchitietsanbong(id_sanbong);
        callback.enqueue(new Callback<List<Chitietsanbong>>() {
            @Override
            public void onResponse(Call<List<Chitietsanbong>> call, Response<List<Chitietsanbong>> response) {
                chitietsanbong=response.body();
                if (chitietsanbong.size()>0){
                    LoadDataonView(chitietsanbong);
                }
            }
            @Override
            public void onFailure(Call<List<Chitietsanbong>> call, Throwable t) {
                Log.d("onFailure_sanbongs", t.getMessage());
            }
        });
    }

    private void LoadDataonView(List<Chitietsanbong> chitietsanbong) {
        Picasso.with(ThongtinSanbong.this).load(chitietsanbong.get(0).getHinhanh())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_hinhanh1);
        Picasso.with(ThongtinSanbong.this).load(chitietsanbong.get(1).getHinhanh())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_hinhanh2);
        ratingBar.setRating(Float.parseFloat(chitietsanbong.get(0).getDanhgia()));
        tv_tenchusan.setText(chitietsanbong.get(0).getTenchusan());
        tv_sdt.setText(sodienthoai);
        tv_diachi.setText(diachi);
        tv_loaisan.setText(chitietsanbong.get(0).getLoaisan());
        tv_tensan.setText(tensan);
        tv_khoanggia.setText(chitietsanbong.get(0).getKhoanggia());
    }


    private void getDataIntent() {
        id_sanbong=getIntent().getStringExtra("id_sanbong");
        tensan=getIntent().getStringExtra("tensanbong");
        diachi=getIntent().getStringExtra("diachi");
        sodienthoai=getIntent().getStringExtra("sodienthoai");
    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        ratingBar=findViewById(R.id.rating_thongtinsanbong_danhgia);
        img_back=findViewById(R.id._img_thongtinsanbong_back);
        img_hinhanh1=findViewById(R.id.img_thongtinsanbong_hinhanh1);
        img_hinhanh2=findViewById(R.id.img_thongtinsanbong_hinhanh2);
        tv_tenchusan=findViewById(R.id.tv_thongtinsanbong_tenchusan);
        tv_tensan=findViewById(R.id.tv_thongtinsanbong_tensan);
        tv_loaisan=findViewById(R.id.tv_thongtinsanbong_loaisan);
        tv_diachi=findViewById(R.id.tv_thongtinsanbong_diachisan);
        tv_sdt=findViewById(R.id.tv_thongtinsanbong_sodienthoai);
        tv_khoanggia=findViewById(R.id.tv_thongtinsanbong_khoanggia);
    }

}
