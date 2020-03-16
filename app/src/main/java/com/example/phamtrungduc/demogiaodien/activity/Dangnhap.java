package com.example.phamtrungduc.demogiaodien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Dangnhap extends AppCompatActivity {
    Button btndangnhap;
    TextView tvdangky,tvforgotpass;
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
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dangnhap.this,Trangchu.class));
            }
        });
        tvforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_fogotpass,null);
                Button btn_forgotpass=view.findViewById(R.id.btn_forgotpass);
                btn_forgotpass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                AlertDialog.Builder diaBuilder= new AlertDialog.Builder(Dangnhap.this);
                diaBuilder.setView(view);
                diaBuilder.setCancelable(true);
                AlertDialog showDialog=diaBuilder.create();
                showDialog.show();
            }
        });
    }

    private void Anhxa() {
        btndangnhap=findViewById(R.id.btn_dangnhap);
        tvdangky=findViewById(R.id.tv_dangky);
        tvforgotpass=findViewById(R.id.tv_dangnhap_forgotpass);
        edtemail=findViewById(R.id.edt_dangnhap_email);
        edtpass=findViewById(R.id.edt_dangnhap_matkhau);
        btnfacebook=findViewById(R.id.imgbtn_facebook);
    }

}
