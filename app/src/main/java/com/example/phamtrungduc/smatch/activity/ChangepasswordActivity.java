package com.example.phamtrungduc.smatch.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.ServiceNotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangepasswordActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText edt_matkhaucu,edt_matkhaumoi,edt_nhaplaimatkhaumoi;
    private CheckBox cb_showpass;
    private Button btn_doipass;
    private ProgressBar pro_doipass;
    private String email_nguoidung="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        AnhXa();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventClick();

    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edt_matkhaumoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edt_matkhaumoi.getText().toString().length()<6){
                    edt_matkhaumoi.setError("Mật khẩu phải lớn hơn 6 ký tự");
                }
            }
        });
        btn_doipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                v.setEnabled(false);
                pro_doipass.setVisibility(View.VISIBLE);
                String matkhaucu=edt_matkhaucu.getText().toString();
                String matkhaumoi=edt_matkhaumoi.getText().toString();
                String nhaplaimatkhau=edt_nhaplaimatkhaumoi.getText().toString();
                if (TextUtils.isEmpty(matkhaucu)||TextUtils.isEmpty(matkhaumoi)||TextUtils.isEmpty(nhaplaimatkhau)){
                    if (TextUtils.isEmpty(matkhaucu)){
                        edt_matkhaucu.setError("Nhập mật khẩu hiện tại");
                        v.setVisibility(View.VISIBLE);
                        v.setEnabled(true);
                        pro_doipass.setVisibility(View.INVISIBLE);
                    }if (TextUtils.isEmpty(matkhaumoi)){
                        edt_matkhaumoi.setError("Nhập mật khẩu mới");
                        v.setVisibility(View.VISIBLE);
                        v.setEnabled(true);
                        pro_doipass.setVisibility(View.INVISIBLE);
                    }if (TextUtils.isEmpty(nhaplaimatkhau)){
                        edt_nhaplaimatkhaumoi.setError("Nhập lại mật khẩu mới");
                        v.setVisibility(View.VISIBLE);
                        v.setEnabled(true);
                        pro_doipass.setVisibility(View.INVISIBLE);
                    }
                    v.setVisibility(View.VISIBLE);
                    v.setEnabled(true);
                    pro_doipass.setVisibility(View.INVISIBLE);
                }else{
                    if (matkhaumoi.length()<6){
                        edt_matkhaumoi.setError("Mật khẩu phải lớn hơn 6 ký tự");
                        v.setVisibility(View.VISIBLE);
                        v.setEnabled(true);
                        pro_doipass.setVisibility(View.INVISIBLE);
                    }else{
                        if (!matkhaumoi.equals(nhaplaimatkhau)){
                            edt_nhaplaimatkhaumoi.setError("Mật khẩu nhập lại phải giống mật khẩu mới");
                            v.setVisibility(View.VISIBLE);
                            v.setEnabled(true);
                            pro_doipass.setVisibility(View.INVISIBLE);
                        }else{
                            v.setVisibility(View.INVISIBLE);
                            v.setEnabled(false);
                            pro_doipass.setVisibility(View.VISIBLE);
                            Doimatkhau(v,email_nguoidung,matkhaucu,matkhaumoi);
                        }
                    }

                }
            }
        });
        cb_showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edt_matkhaucu.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_matkhaumoi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_nhaplaimatkhaumoi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edt_matkhaucu.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_matkhaumoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_nhaplaimatkhaumoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void Doimatkhau(final View v, String email_nguoidung, final String matkhaucu, final String matkhaumoi) {
        DataClient dataClient=APIUntils.getData();
        Call<String> callback=dataClient.nguoidung_doimatkhau(email_nguoidung,matkhaucu,matkhaumoi);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess=response.body();
                Log.d("doipass", mess);
                if (mess.equals("oldpass_error")){
                    edt_matkhaucu.setError("Mật khẩu hiện tại không đúng");
                    edt_matkhaucu.setText("");
                    v.setVisibility(View.VISIBLE);
                    v.setEnabled(true);
                    pro_doipass.setVisibility(View.INVISIBLE);
                }if (mess.equals("equals")){
                    edt_matkhaumoi.setText("");
                    edt_matkhaumoi.setError("Mật khẩu mới không thể giống mật khẩu hiện tại");
                    v.setVisibility(View.VISIBLE);
                    v.setEnabled(true);
                    pro_doipass.setVisibility(View.INVISIBLE);
                }if (mess.equals("success")){
                    v.setVisibility(View.INVISIBLE);
                    v.setEnabled(false);
                    pro_doipass.setVisibility(View.VISIBLE);
                    DoimatkhauFirebase(v,matkhaucu,matkhaumoi);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("doipassfail", t.getMessage());
            }
        });
    }

    private void DoimatkhauFirebase(final View v, String matkhaucu, final String matkhaumoi) {
        AuthCredential credential=EmailAuthProvider.getCredential(MyFirebaseMessagingService.mUser.getEmail(),matkhaucu);
        MyFirebaseMessagingService.mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    MyFirebaseMessagingService.mUser.updatePassword(matkhaumoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pro_doipass.setVisibility(View.GONE);
                                Toast.makeText(ChangepasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangepasswordActivity.this,HomeActivity.class));
                            }else{
                                Toast.makeText(ChangepasswordActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                v.setVisibility(View.VISIBLE);
                                v.setEnabled(true);
                                pro_doipass.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

    }

    private void AnhXa() {
        img_back = findViewById(R.id._img_changepass_back);
        edt_matkhaucu=findViewById(R.id.edt_doipass_passcu);
        edt_matkhaumoi=findViewById(R.id.edt_doipass_passmoi);
        edt_nhaplaimatkhaumoi=findViewById(R.id.edt_doipass_xacnhanmoi);
        cb_showpass=findViewById(R.id.cb_doipass_showpass);
        btn_doipass=findViewById(R.id.btn_doipass);
        pro_doipass=findViewById(R.id.progress_doipass);
        pro_doipass.setVisibility(View.INVISIBLE);
        email_nguoidung=MyFirebaseMessagingService.mUser.getEmail();
    }
}
