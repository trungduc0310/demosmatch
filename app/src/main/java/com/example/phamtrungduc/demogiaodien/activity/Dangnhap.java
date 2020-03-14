package com.example.phamtrungduc.demogiaodien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;

import androidx.appcompat.app.AppCompatActivity;

public class Dangnhap extends AppCompatActivity {
    Button btndangnhap;
    TextView tvdangky;
    EditText edtemail, edtpass;
    ImageButton btnfacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        Anhxa();
        Xulysukien();


    }

    private void Xulysukien() {
        tvdangky.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                tvdangky.setBackgroundColor(R.color.background_dangky_action);
                Intent intent= new Intent(Dangnhap.this,Dangky.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        btndangnhap=findViewById(R.id.btn_dangnhap);
        tvdangky=findViewById(R.id.tv_dangky);
        edtemail=findViewById(R.id.edt_dangnhap_email);
        edtpass=findViewById(R.id.edt_dangnhap_matkhau);
        btnfacebook=findViewById(R.id.imgbtn_facebook);
    }

}
