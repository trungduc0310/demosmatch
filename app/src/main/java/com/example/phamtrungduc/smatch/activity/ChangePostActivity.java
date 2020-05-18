package com.example.phamtrungduc.smatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.squareup.picasso.Picasso;

public class ChangePostActivity extends AppCompatActivity {
    CircleImageView img_avt;
    TextView tv_username,tv_datetime;
    ImageView img_back,img_hinhanhbaiviet;
    Button btn_save;
    EditText edt_noidung;
    ProgressBar pro_chinhsua;
    String id_baiviet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinhsuabaiviet);
        AnhXa();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
        eventClick();
    }

    private void eventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                onBackPressed();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro_chinhsua.setVisibility(View.VISIBLE);
                String noidung=edt_noidung.getText().toString();
                Luubaiviet(id_baiviet,noidung);
                onStop();
            }
        });
        edt_noidung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edt_noidung.getText().toString().length()>0){
                    btn_save.setEnabled(true);
                }else{
                    btn_save.setEnabled(false);
                }
            }
        });
    }

    private void Luubaiviet(String id_baiviet, String noidung) {
        DataClient dataClient=APIUntils.getData();
        Call<String> callback=dataClient.nguoidung_chinhsuabaiviet(id_baiviet,noidung);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pro_chinhsua.setVisibility(View.GONE);
                String mess=response.body();
                Log.d("capnhatbv", mess);
                if (mess.equals("success")) {
                    startActivity(new Intent(ChangePostActivity.this,HomeActivity.class));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void getData() {
        id_baiviet=getIntent().getStringExtra("id_baiviet");
        String noidung=getIntent().getStringExtra("noidung");
        String hinhanhbaiviet=getIntent().getStringExtra("hinhanh");
        String thoigian=getIntent().getStringExtra("ngaythang");
        String username=HomeActivity.mUser.getDisplayName();
        String avt= String.valueOf(HomeActivity.mUser.getPhotoUrl());
        edt_noidung.setText(noidung);
        if (TextUtils.isEmpty(hinhanhbaiviet)){
            img_hinhanhbaiviet.setVisibility(View.GONE);
        }else{
            Picasso.with(ChangePostActivity.this).load(hinhanhbaiviet)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(img_hinhanhbaiviet);
        }

        Picasso.with(ChangePostActivity.this).load(avt)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_avt);
        tv_username.setText(username);
        tv_datetime.setText(thoigian);

    }

    private void AnhXa() {
        img_avt=findViewById(R.id.img_chinhsua_avtbaiviet);
        tv_username=findViewById(R.id.tv_chinhsua_username);
        tv_datetime=findViewById(R.id.tv_chinhsua_ngaythang);
        img_back=findViewById(R.id._img_chinhsua_back);
        img_hinhanhbaiviet=findViewById(R.id.img_chinhsua_hinhanhbaiviet);
        btn_save=findViewById(R.id.btn_chinhsua_save);
        edt_noidung=findViewById(R.id.edt_chinhsua_noidung);
        pro_chinhsua=findViewById(R.id.progress_chinhsua);
        pro_chinhsua.setVisibility(View.INVISIBLE);
    }
}
