package com.example.phamtrungduc.demogiaodien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.Retrofit2.APIUntils;
import com.example.phamtrungduc.demogiaodien.Retrofit2.DataClient;
import com.example.phamtrungduc.demogiaodien.entity.Nguoidung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dangnhap extends AppCompatActivity {
    Button btndangnhap;
    TextView tvdangky,tvforgotpass;
    EditText edtemail, edtpass;
    ProgressBar progress_dangnhap;
    List<Nguoidung> thongtinnguoidung;
    FirebaseAuth mAuth;
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
//                tvdangky.setBackgroundColor(R.color.background_dangky_action);
                Intent intent= new Intent(Dangnhap.this,Dangky.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                v.setVisibility(View.INVISIBLE);
                progress_dangnhap.setVisibility(View.VISIBLE);
                String email=edtemail.getText().toString();
                String password=edtpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    if (TextUtils.isEmpty(email)) edtemail.setError("Vui lòng nhập email");
                    if (TextUtils.isEmpty(password)) edtpass.setError("Vui lòng nhập password");
                    v.setEnabled(true);
                    v.setVisibility(View.VISIBLE);
                    progress_dangnhap.setVisibility(View.INVISIBLE);

                }
                else{
                    if (password.length()<6){
                        v.setEnabled(true);
                        v.setVisibility(View.VISIBLE);
                        progress_dangnhap.setVisibility(View.INVISIBLE);
                        edtpass.setError("Vui lòng nhập lại mật khẩu");
                    }
                    else{
                        DangnhapFirebase(v,email,password);
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

    private void DangnhapFirebase(final View v, final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    DangnhapMySQL(v,email,password);
                }
                else{
                    v.setEnabled(true);
                    v.setVisibility(View.VISIBLE);
                    progress_dangnhap.setVisibility(View.GONE);
                    Toast.makeText(Dangnhap.this, "Sai tài khoản, Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void DangnhapMySQL(final View v, String email, String password) {
        DataClient dataClient=APIUntils.getData();
        Call<List<Nguoidung>> callback=dataClient.nguoidung_dangnhap(email,password);
        callback.enqueue(new Callback<List<Nguoidung>>() {
            @Override
            public void onResponse(Call<List<Nguoidung>> call, Response<List<Nguoidung>> response) {
                v.setEnabled(true);
                progress_dangnhap.setVisibility(View.GONE);
                thongtinnguoidung=response.body();
                Intent intent= new Intent(Dangnhap.this,Trangchu.class);
                intent.putParcelableArrayListExtra("thongtinnguoidung", (ArrayList<? extends Parcelable>) thongtinnguoidung);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Nguoidung>> call, Throwable t) {
                Log.d("dangnhap", t.getMessage());
                v.setEnabled(true);
                progress_dangnhap.setVisibility(View.GONE);
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
        progress_dangnhap=findViewById(R.id.progress_dangnhap);
    }

}
