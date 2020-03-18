package com.example.phamtrungduc.demogiaodien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Dangnhap extends AppCompatActivity {
    Button btndangnhap;
    TextView tvdangky,tvforgotpass;
    EditText edtemail, edtpass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
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
                String email=edtemail.getText().toString();
                String password=edtpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    if (TextUtils.isEmpty(email)) edtemail.setError("Vui lòng nhập email");
                    if (TextUtils.isEmpty(password)) edtpass.setError("Vui lòng nhập password");
                }
                else{
                    if (password.length()<6) edtpass.setError("Vui lòng nhập lại mật khẩu");
                    else{
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    startActivity(new Intent(Dangnhap.this,Trangchu.class));
                                }
                                else{
                                    Toast.makeText(Dangnhap.this, "Sai tài khoản, Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
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
        mAuth=FirebaseAuth.getInstance();
    }

}
