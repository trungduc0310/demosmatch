package com.example.phamtrungduc.smatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.view.View.GONE;

public class UserDetailsActivity extends AppCompatActivity {
    ImageView img_back;
    CircleImageView img_avt;
    TextView tv_changeavt;
    EditText edt_username;
    EditText edt_email;
    EditText edt_diachi;
    EditText edt_ngaysinh;
    CalendarView cld_ngaysinh;
    Button btn_save;
    RadioButton rbtn_nam,rbtn_nu,rbtn_khac;
    ProgressBar pro_thongtin;

    private int REQUEST_CODE_IMAGE = 1;
    String real_path="";
    String new_avt;
    String old_avt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinnguoidung);
        AnhXa();
        getData();
        EventClick();
    }


    private void getData() {
        old_avt= String.valueOf(HomeActivity.mUser.getPhotoUrl());
        String email=HomeActivity.mUser.getEmail();
//        edt_email.setText(email);
        old_avt=old_avt.substring(old_avt.lastIndexOf("/"));
        getThongtinnguoidung(email);
    }

    private void getThongtinnguoidung(String email) {
        DataClient dataClient= APIUntils.getData();
        Call<List<User>> callback= dataClient.get_nguoidung(email.trim());
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> thongtinnguoidung=response.body();
                Log.d("getDAta", thongtinnguoidung.get(0).getTennguoidung());
                edt_username.setText(thongtinnguoidung.get(0).getTennguoidung());
                edt_email.setText(thongtinnguoidung.get(0).getEmail());
                try {
                    edt_diachi.setText(thongtinnguoidung.get(0).getDiachi());
                }catch (NullPointerException nullex){
                    edt_diachi.setText("");
                }
                try {
                    String[]xuly=thongtinnguoidung.get(0).getNgaysinh().split("\\-");
                    String ngaythang=xuly[2]+"-"+xuly[1]+"-"+xuly[0];
                    edt_ngaysinh.setText(ngaythang);

                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Integer.parseInt(xuly[0])-0,Integer.parseInt(xuly[1])-1,Integer.parseInt(xuly[2])-0);
                    cld_ngaysinh.setDate(calendar.getTime().getTime());
                }catch (NullPointerException nullex){
                    edt_ngaysinh.setText("");

                }
                try {
                    if (thongtinnguoidung.get(0).getGioitinh().equals("Nam")) rbtn_nam.setChecked(true);
                else if (thongtinnguoidung.get(0).getGioitinh().equals("Nữ")) rbtn_nu.setChecked(true);
                else if (thongtinnguoidung.get(0).getGioitinh().equals("Khác")) rbtn_khac.setChecked(true);
                }catch (NullPointerException nullex){

                }
                try {
                    Picasso.with(UserDetailsActivity.this).load(thongtinnguoidung.get(0).getAnhdaidien())
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .error(R.drawable.ic_broken_image_black_24dp)
                        .into(img_avt);
                }catch (NullPointerException nullex){

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("getDAtafail", t.getMessage());
            }
        });
    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_changeavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UserDetailsActivity.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                } else {
                    ActivityCompat.requestPermissions(UserDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro_thongtin.setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);
                String username=edt_username.getText().toString();
                String gioitinh="";
                String diachi=edt_diachi.getText().toString();

                String[] xuly=edt_ngaysinh.getText().toString().split("\\-");
                String ngaysinh=xuly[2]+"-"+xuly[1]+"-"+xuly[0];

                String email=HomeActivity.mUser.getEmail();
                if (rbtn_nu.isChecked()) gioitinh="Nữ";
                else if (rbtn_nam.isChecked()) gioitinh="Nam";
                else if (rbtn_khac.isChecked()) gioitinh="Khác";
                else gioitinh="";
                UpdateMySQL(email,username,gioitinh,diachi,ngaysinh);
            }
        });
    }

    private void UpdateMySQL(final String email, final String username, final String gioitinh, final String diachi, final String ngaysinh) {
        try{
//            if (!TextUtils.isEmpty(real_path)){
                File file = new File(real_path);
                String file_path = file.getAbsolutePath();
                String[] mangtenFile = file_path.split("\\.");
                file_path = mangtenFile[0] + "_anhdaidien_" + System.currentTimeMillis() + "." + mangtenFile[1];
                Log.d("anhdaidien", file_path);

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part mulPart = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

                final DataClient dataClient = APIUntils.getData();
                Call<String> callback = dataClient.uploadImg_anhdaidien(mulPart);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String message = response.body();
                        Log.d("src_anhdaidienmoi", message);
                        if (message.length() > 0) {
                            new_avt = APIUntils.base_url + "image/image_anhdaidien/" + message;
//                            Log.d("capnhat1_email", email);
//                            Log.d("capnhat1_user", username);
//                            Log.d("capnhat1_gioitinh", gioitinh);
//                            Log.d("capnhat1_diachi", diachi);
//                            Log.d("capnhat1_ngaysinh", ngaysinh);
//                            Log.d("capnhat1_oldavt", old_avt);
//                            Log.d("capnhat1_newavt", new_avt);
                            DataClient dataClient1 = APIUntils.getData();
                            Call<String> callback= dataClient1.nguoidung_capnhathoso(email,username,gioitinh,diachi,ngaysinh,old_avt,new_avt);
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String mess=response.body();
                                    Log.d("capnhat1", mess);
                                    if (mess.equals("success")){
                                        UpdateFirebase1(username,new_avt);
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("capnhatfail", t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("uploadanhfail", t.getMessage());
                    }
                });


        }catch (ArrayIndexOutOfBoundsException nullex){
//            Toast.makeText(this, real_path, Toast.LENGTH_SHORT).show();
            Log.d("null", real_path);
            DataClient dataClient1 = APIUntils.getData();
            Call<String> callback= dataClient1.nguoidung_capnhathoso(email,username,gioitinh,diachi,ngaysinh,old_avt,"");
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String mess=response.body();
                    Log.d("capnhat2", mess);
                    if (mess.equals("success")){
                        UpdateFirebase2(username);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("capnhat2fail", t.getMessage());
                }
            });
        }

    }

    private void UpdateFirebase2(String username) {
        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        HomeActivity.mUser.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pro_thongtin.setVisibility(GONE);
                btn_save.setVisibility(View.VISIBLE);
//                Toast.makeText(UserDetailsActivity.this, "Cập nhật fb thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserDetailsActivity.this,HomeActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserDetailsActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateFirebase1(String username, String new_avt) {
            UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(new_avt))
                    .setDisplayName(username)
                    .build();
            HomeActivity.mUser.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    pro_thongtin.setVisibility(GONE);
                    btn_save.setVisibility(View.VISIBLE);
                    startActivity(new Intent(UserDetailsActivity.this,HomeActivity.class));
                    onStop();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserDetailsActivity.this, "Cập nhật fb thất bại", Toast.LENGTH_SHORT).show();

                }
            });
    }

    private void AnhXa() {
        img_back=findViewById(R.id._img_thongtinnguoidung_back);
        img_avt=findViewById(R.id.img_thongtinnguoidung_hinhanh);
        tv_changeavt=findViewById(R.id.tv_thongtinnguoidung_changeavt);
        edt_username=findViewById(R.id.edt_thongtinnguoidung_content_ten);
        edt_email=findViewById(R.id.edt_thongtinnguoidung_content_email);
        edt_diachi=findViewById(R.id.edt_thongtinnguoidung_content_diachi);
        edt_ngaysinh=findViewById(R.id.edt_thongtinnguoidung_ngaysinh);
        cld_ngaysinh=findViewById(R.id.cld_thongtinnguoidung);
        btn_save=findViewById(R.id.btn_thongtinnguoidung_save);
        rbtn_nam=findViewById(R.id.radio_thongtinnguoidung_nam);
        rbtn_nu=findViewById(R.id.radio_thongtinnguoidung_nu);
        rbtn_khac=findViewById(R.id.radio_thongtinnguoidung_khac);
        pro_thongtin=findViewById(R.id.progress_thongtinnguoidung);
        pro_thongtin.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            real_path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_avt.setImageBitmap(bitmap);
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
