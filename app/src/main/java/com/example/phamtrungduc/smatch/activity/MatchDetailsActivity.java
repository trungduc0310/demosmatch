package com.example.phamtrungduc.smatch.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.entity.MatchDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchDetailsActivity extends AppCompatActivity {
    ProgressBar pro_datsan;
    RatingBar ratingBar;
    ImageView img_back, img_hinhanh1, img_hinhanh2;
    TextView tv_tensan, tv_loaisan, tv_tenchusan, tv_diachi, tv_sdt, tv_khoanggia;
    String id_sanbong, tensan, diachi, sodienthoai, src1, src2;
    Button btn_call;

    List<MatchDetails> matchDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thongtinsanbong);
        super.onCreate(savedInstanceState);
        AnhXa();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataIntent();
        getData();
        EventClick();
    }

    private void getData() {
        DataClient dataClient = APIUntils.getData();
        final Call<List<MatchDetails>> callback = dataClient.sanbong_getchitietsanbong(id_sanbong);
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.enqueue(new Callback<List<MatchDetails>>() {
                    @Override
                    public void onResponse(Call<List<MatchDetails>> call, Response<List<MatchDetails>> response) {
                        matchDetails = response.body();
                        if (matchDetails.size() > 0) {
                            src1 = matchDetails.get(0).getHinhanh();
                            src2 = matchDetails.get(1).getHinhanh();
                            img_hinhanh1.post(new Runnable() {
                                @Override
                                public void run() {
                                    LoadDataonView(matchDetails);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MatchDetails>> call, Throwable t) {
                        Log.d("onFailure_sanbongs", t.getMessage());
                    }
                });

            }
        }).start();
    }

    private void LoadDataonView(List<MatchDetails> matchDetails) {
        Picasso.with(MatchDetailsActivity.this).load(matchDetails.get(0).getHinhanh())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_hinhanh1);
        Picasso.with(MatchDetailsActivity.this).load(matchDetails.get(1).getHinhanh())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_hinhanh2);
        ratingBar.setRating(Float.parseFloat(matchDetails.get(0).getDanhgia()));
        tv_tenchusan.setText(matchDetails.get(0).getTenchusan());
        tv_sdt.setText(sodienthoai);
        tv_diachi.setText(diachi);
        tv_loaisan.setText(matchDetails.get(0).getLoaisan());
        tv_tensan.setText(tensan);
        tv_khoanggia.setText(matchDetails.get(0).getKhoanggia());
    }


    private void getDataIntent() {
        id_sanbong = getIntent().getStringExtra("id_sanbong");
        tensan = getIntent().getStringExtra("tensanbong");
        diachi = getIntent().getStringExtra("diachi");
        sodienthoai = getIntent().getStringExtra("sodienthoai");
    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
                onBackPressed();
            }
        });
        img_hinhanh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowdialogIMG(src1);
            }
        });
        img_hinhanh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowdialogIMG(src2);
            }
        });
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                pro_datsan.setVisibility(View.VISIBLE);
                DatSan(id_sanbong);
            }
        });
    }
    private void DatSan(String id_sanbong) {
        DataClient dataClient=APIUntils.getData();
        Call<String> callback=dataClient.sanbong_datsan(Integer.parseInt(id_sanbong));
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess=response.body();
                Log.d("datsan", mess);
                if (mess.equals("success")){
                    pro_datsan.setVisibility(View.GONE);
                    btn_call.setVisibility(View.VISIBLE);
                    CallMobile(sodienthoai);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("datsan", t.getMessage());
            }
        });
    }

    private void CallMobile(String sodienthoai) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" +sodienthoai));
        startActivity(intent);
    }

    private void ShowdialogIMG(String src) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_xemhinhanh, null);
        ImageView imgview_dialog=view.findViewById(R.id.img_dialogxemanh_img);
        Picasso.with(MatchDetailsActivity.this).load(src)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(imgview_dialog);
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(MatchDetailsActivity.this);
        diaBuilder.setView(view);
        diaBuilder.setCancelable(true);
        AlertDialog showDialog = diaBuilder.create();
        showDialog.show();
    }

    private void AnhXa() {
        ratingBar = findViewById(R.id.rating_thongtinsanbong_danhgia);
        img_back = findViewById(R.id._img_thongtinsanbong_back);
        img_hinhanh1 = findViewById(R.id.img_thongtinsanbong_hinhanh1);
        img_hinhanh2 = findViewById(R.id.img_thongtinsanbong_hinhanh2);
        tv_tenchusan = findViewById(R.id.tv_thongtinsanbong_tenchusan);
        tv_tensan = findViewById(R.id.tv_thongtinsanbong_tensan);
        tv_loaisan = findViewById(R.id.tv_thongtinsanbong_loaisan);
        tv_diachi = findViewById(R.id.tv_thongtinsanbong_diachisan);
        tv_sdt = findViewById(R.id.tv_thongtinsanbong_sodienthoai);
        tv_khoanggia = findViewById(R.id.tv_thongtinsanbong_khoanggia);
        btn_call=findViewById(R.id.btn_thongtinsanbong_lienhengay);
        pro_datsan=findViewById(R.id.progress_datsan);
        pro_datsan.setVisibility(View.INVISIBLE);
    }

}
