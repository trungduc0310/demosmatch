package com.example.phamtrungduc.smatch.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class RegisterActivity extends AppCompatActivity {
    EditText edt_username, edt_email, edt_pass, edt_repass;
    Button btn_register;
    ImageView img_back, img_anhdaidien;
    TextView tv_chonanhdaidien;
    CheckBox cb_showpass;
    FirebaseAuth mAuth;
    private int REQUEST_CODE_IMAGE = 1;
    String real_path;
    String anhdaidien=null;
    String id_nguoidung=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        Anhxa();
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
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String username = edt_username.getText().toString();
                String password = edt_pass.getText().toString().trim();
                String repassword = edt_repass.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword) || TextUtils.isEmpty(username)) {
                    if (TextUtils.isEmpty(email)) edt_email.setError("Vui lòng nhập email");
                    if (TextUtils.isEmpty(password)) edt_email.setError("Vui lòng nhập password");
                    if (TextUtils.isEmpty(repassword))
                        edt_email.setError("Vui lòng nhập lại password");
                    if (TextUtils.isEmpty(username))
                        edt_email.setError("Vui lòng nhập tên người dùng");
                } else {
                    if (!password.equals(repassword)) {
                        edt_repass.setError("Mật khẩu nhập lại không đúng");
                    } else {
                        dangkyMySQL(username, email, password);
                    }
                }

            }
        });
        tv_chonanhdaidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                } else {
                    ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        cb_showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_repass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_repass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void dangkyMySQL(final String username, final String email, final String password) {
            try{
                File file = new File(real_path);
                String file_path = file.getAbsolutePath();
                String[] mangtenFile = file_path.split("\\.");
                file_path = mangtenFile[0] + "_anhdaidien_" + System.currentTimeMillis() + "." + mangtenFile[1];
                id_nguoidung="user"+System.currentTimeMillis();
                Log.d("anhdaidien", file_path);

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part mulPart = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

                DataClient dataClient = APIUntils.getData();
                Call<String> callback = dataClient.uploadImg_anhdaidien(mulPart);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String message = response.body();
                        if (message.length() > 0) {
                            anhdaidien=APIUntils.base_url + "image/image_anhdaidien/" + message;
                            DataClient dataClient1 = APIUntils.getData();
                            Call<String> callback = dataClient1.nguoidung_dangky(id_nguoidung,email.trim(), password, username,anhdaidien );
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String thongbao = response.body();
                                    if (thongbao.equals("success")){
                                        dangkyFirebase(email.trim(),password,username,anhdaidien);

                                    }
//                                    Log.d("dangkysuc", thongbao);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("faildangky", t.getMessage());
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("failanhdaidien", t.getMessage());
                    }
                });
            }catch (NullPointerException nullex){
                id_nguoidung="user"+System.currentTimeMillis();
                anhdaidien=APIUntils.base_url + "image/image_anhdaidien/baseuser.png";
                DataClient dataClient1 = APIUntils.getData();
                Call<String> callback = dataClient1.nguoidung_dangky(id_nguoidung,email, password, username,anhdaidien);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String thongbao = response.body();
                        if (thongbao.equals("success")){
                            dangkyFirebase(email,password,username,anhdaidien);
                        }
//
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("faildangky", t.getMessage());
                    }
                });
            }


    }

    private void dangkyFirebase(String email, String password, final String username, final String anhdaidien) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                Toast.makeText(RegisterActivity.this, "Vui lòng kiểm tra email để xác thực tài khoản", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user= mAuth.getCurrentUser();
                                    UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(Uri.parse(anhdaidien))
                                        .setDisplayName(username)
                                        .build();
                                    user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                            onStop();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("faildangkyfire", e.toString());
                                            Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    private void Anhxa() {
        edt_email = findViewById(R.id.edt_dangky_email);
        edt_pass = findViewById(R.id.edt_dangky_matkhau);
        edt_repass = findViewById(R.id.edt_dangky_nhaplaimatkhau);
        edt_username = findViewById(R.id.edt_dangky_tennguoidung);
        img_back = findViewById(R.id._img_dangky_back);
        btn_register = findViewById(R.id.btn_dangky_dangky);
        img_anhdaidien = findViewById(R.id.img_dangky_avt);
        tv_chonanhdaidien = findViewById(R.id.tv_dangky_chonanhdaidien);
        cb_showpass=findViewById(R.id.cb_dangky_showpass);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            real_path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_anhdaidien.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
