package com.example.phamtrungduc.demogiaodien.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.fragment.Bangtin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class Dangky extends AppCompatActivity {
    EditText edt_username,edt_email,edt_pass,edt_repass;
    Button btn_register;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Anhxa();
        EventClick();
    }

    private void EventClick() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangky();
            }
        });
    }

    private void dangky() {
        String email=edt_email.getText().toString();
        String username=edt_username.getText().toString();
        String password=edt_pass.getText().toString().trim();
        String repassword=edt_repass.getText().toString().trim();
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(repassword)||TextUtils.isEmpty(username)){
            if (TextUtils.isEmpty(email)) edt_email.setError("Vui lòng nhập email");
            if (TextUtils.isEmpty(password)) edt_email.setError("Vui lòng nhập password");
            if (TextUtils.isEmpty(repassword)) edt_email.setError("Vui lòng nhập lại password");
            if (TextUtils.isEmpty(username)) edt_email.setError("Vui lòng nhập tên người dùng");
        }
        else {
            if (!password.equals(repassword)){
                edt_repass.setError("Mật khẩu nhập lại không đúng");
            }else{
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(Dangky.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Dangky.this,Dangnhap.class));
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    private void Anhxa() {
        edt_email=findViewById(R.id.edt_dangky_email);
        edt_pass=findViewById(R.id.edt_dangky_matkhau);
        edt_repass=findViewById(R.id.edt_dangky_nhaplaimatkhau);
        edt_username=findViewById(R.id.edt_dangky_tennguoidung);
        btn_register=findViewById(R.id.btn_dangky_dangky);
        mAuth=FirebaseAuth.getInstance();
    }
}
