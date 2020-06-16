package com.example.phamtrungduc.smatch.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.servicenotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.entity.Message;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.example.phamtrungduc.smatch.adapter.AreaAdapter;
import com.example.phamtrungduc.smatch.entity.Area;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class AddPostActivity extends AppCompatActivity {
    private ImageView img_back, img_inserthinhanh;
    private CircleImageView img_avt;
    private TextView tv_username;
    private CheckBox cb_timtrandau;
    private EditText edt_noidung;
    private LinearLayout ln_timkhuvuc;
    private ProgressBar pro_thembaiviet;
    private Button btn_insert;
    private Spinner spinner_khuvuc;
    private List<Area> danhsachkhuvuc;
    private AreaAdapter areaAdapter;
    private final int REQUEST_CODE_IMAGE = 1;
    private String real_path;
    private String anhbaiviet = null;
    private String id_baiviet = null;
    private String tieude = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dangbaiviet);
        super.onCreate(savedInstanceState);
        AnhXa();
        SetDataSpinner();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getProfile();
        EventClick();
    }

    private void getProfile() {
        Picasso.with(this).load(String.valueOf(MyFirebaseMessagingService.mUser.getPhotoUrl()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_avt);
        tv_username.setText(MyFirebaseMessagingService.mUser.getDisplayName());
    }

    private void SetDataSpinner() {
        DataClient dataClient = APIUntils.getData();
        Call<List<Area>> callback = dataClient.sanbong_getkhuvuc();
        callback.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                danhsachkhuvuc = response.body();
                if (danhsachkhuvuc.size() > 0) {
                    Log.d("khuvuc", "success");
                    areaAdapter = new AreaAdapter(AddPostActivity.this, R.layout.item_khuvuc, danhsachkhuvuc);

                    spinner_khuvuc.setAdapter(areaAdapter);
                } else {
                    Toast.makeText(AddPostActivity.this, "Không có khu vưc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Log.d("onFailure khuvuc", t.getMessage());
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
        cb_timtrandau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_timtrandau.isChecked()) {
                    ln_timkhuvuc.setVisibility(View.VISIBLE);
                } else {
                    ln_timkhuvuc.setVisibility(View.INVISIBLE);
                }
            }
        });
        img_inserthinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddPostActivity.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                } else {
                    ActivityCompat.requestPermissions(AddPostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        edt_noidung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if (edt_noidung.getText().toString().length() > 0) {
                    btn_insert.setBackgroundResource(R.drawable.btn_binhluan_active);
                    btn_insert.setTextColor(R.color.white);
                    btn_insert.setEnabled(true);
                } else {
                    btn_insert.setBackgroundResource(R.drawable.btn_binhluan);
                    btn_insert.setTextColor(R.color.gray);
                    btn_insert.setEnabled(false);
                }
            }
        });
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro_thembaiviet.setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);
                setTieude();
                Themtin();
            }
        });
    }

    private void setTieude() {
        if (cb_timtrandau.isChecked()) {
            Area area = (Area) spinner_khuvuc.getSelectedItem();
            tieude = "Trận đấu tại khu vực: " + area.getTenkhuvuc();
        }
    }

    private void Themtin() {
        final String noidung = edt_noidung.getText().toString();
        id_baiviet = "bv_" + System.currentTimeMillis();
        try{
            if (real_path.length() > 0) {
                File file = new File(real_path);
                String file_path = file.getAbsolutePath();
                String[] mangtenFile = file_path.split("\\.");
                file_path = mangtenFile[0] + "_anhbaiviet_" + System.currentTimeMillis() + "." + mangtenFile[1];

                Log.d("anhbaiviet", file_path);

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part mulPart = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

                final DataClient dataClient = APIUntils.getData();
                Call<String> callback = dataClient.uploadImg_anhbaiviet(mulPart);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String message = response.body();
                        if (message.length() > 0) {
                            anhbaiviet = APIUntils.base_url + "image/image_hinhanhbaiviet/" + message;
                            DataClient dataClient1 = APIUntils.getData();
                            if (TextUtils.isEmpty(tieude)){
                                Call<String> callback = dataClient1.nguoidung_thembaiviet(id_baiviet, MyFirebaseMessagingService.mUser.getEmail(), noidung, tieude, anhbaiviet);
                                callback.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("onFailurethembv:", t.getMessage());
                                    }
                                });
                            }else{
                                final String title=tieude;
                                final String body="Nhấn để nhận kèo thôi nào!";
                                Call<String> callback = dataClient1.nguoidung_thembaiviet(id_baiviet, MyFirebaseMessagingService.mUser.getEmail(), noidung, tieude, anhbaiviet);
                                callback.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String topics="/topics/news";
                                        DataClient dataClient2= APIUntils.getData();
                                        Call<Message> callback= dataClient2.push_notification(topics,title,body,id_baiviet,MyFirebaseMessagingService.mUser.getEmail(),anhbaiviet,MyFirebaseMessagingService.mUser.getDisplayName(),noidung,tieude, String.valueOf(MyFirebaseMessagingService.mUser.getPhotoUrl()));
                                        callback.enqueue(new Callback<Message>() {
                                            @Override
                                            public void onResponse(Call<Message> call, Response<Message> response) {
                                                pro_thembaiviet.setVisibility(View.GONE);
                                                btn_insert.setVisibility(View.VISIBLE);
                                                FirebaseMessaging.getInstance().subscribeToTopic(id_baiviet);
                                                startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                                            }
                                            @Override
                                            public void onFailure(Call<Message> call, Throwable t) {
                                                Log.d("push_notification", t.getMessage());
                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("onFailurethembv:", t.getMessage());
                                    }
                                });
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("onFailurethemhabv:", t.getMessage());
                        pro_thembaiviet.setVisibility(View.GONE);
                        btn_insert.setVisibility(View.VISIBLE);
                        Toast.makeText(AddPostActivity.this, "Đường truyền kém, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch (NullPointerException nex){
            DataClient dataClient1 = APIUntils.getData();
            if (TextUtils.isEmpty(tieude)){
                Call<String> callback = dataClient1.nguoidung_thembaiviet(id_baiviet, MyFirebaseMessagingService.mUser.getEmail(), noidung, tieude, anhbaiviet);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        pro_thembaiviet.setVisibility(View.GONE);
                        btn_insert.setVisibility(View.VISIBLE);
                        startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("onFailurethembv:", t.getMessage());
                    }
                });
            }else{
                final String title=tieude;
                final String body="Nhấn để nhận kèo thôi nào!";
                Call<String> callback = dataClient1.nguoidung_thembaiviet(id_baiviet, MyFirebaseMessagingService.mUser.getEmail(), noidung, tieude, anhbaiviet);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String topics="/topics/news";
                        DataClient dataClient2= APIUntils.getData();
                        Call<Message> callback= dataClient2.push_notification(topics,title,body,id_baiviet,MyFirebaseMessagingService.mUser.getEmail(),anhbaiviet,MyFirebaseMessagingService.mUser.getDisplayName(),noidung,tieude, String.valueOf(MyFirebaseMessagingService.mUser.getPhotoUrl()));
                        callback.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                pro_thembaiviet.setVisibility(View.GONE);
                                btn_insert.setVisibility(View.VISIBLE);
                                FirebaseMessaging.getInstance().subscribeToTopic(id_baiviet);
                                startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                            }
                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
//                                                startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                                pro_thembaiviet.setVisibility(View.GONE);
                                btn_insert.setVisibility(View.VISIBLE);
                                startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                                Log.d("push_notification", t.getMessage());

                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("onFailurethembv:", t.getMessage());
                    }
                });
            }
        }

    }


    private void AnhXa() {
        img_back = findViewById(R.id._img_dangbaiviet_back);
        cb_timtrandau = findViewById(R.id.check_dangbaiviet_timtran);
        ln_timkhuvuc = findViewById(R.id.ln_dangbai_chonkhuvuc);
        ln_timkhuvuc.setVisibility(View.INVISIBLE);
        img_inserthinhanh = findViewById(R.id.img_dangbaiviet_themanh);
        btn_insert = findViewById(R.id.btn_dangbaiviet_insert);
        spinner_khuvuc = findViewById(R.id.spin_dangbai_khuvuc);
        tv_username = findViewById(R.id.tv_dangbai_name);
        img_avt = findViewById(R.id.img_dangbai_avt);
        pro_thembaiviet=findViewById(R.id.progress_thembaiviet);
        edt_noidung = findViewById(R.id.edt_dangbaiviet_noidung);
        pro_thembaiviet.setVisibility(View.INVISIBLE);
        btn_insert.setEnabled(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            real_path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_inserthinhanh.setImageBitmap(bitmap);
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
