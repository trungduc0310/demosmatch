package com.example.phamtrungduc.demogiaodien.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.Retrofit2.APIUntils;
import com.example.phamtrungduc.demogiaodien.Retrofit2.DataClient;
import com.example.phamtrungduc.demogiaodien.adapter.AdapterBinhluan;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.example.phamtrungduc.demogiaodien.entity.Binhluan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChitietBaiviet extends AppCompatActivity {
    ListView lv_dscmt;
    List<Binhluan> mlist;
    AdapterBinhluan adapter;
    ProgressBar progressBar_comment;
    ImageView img_back, img_avt_user, img_hinhanh, img_avt;
    ImageButton img_send, img_more;
    TextView tv_username, tv_thoigian, tv_noidung;
    EditText edt_nhapcmt;
    String id_baiviet;
    String hinhanhbaiviet;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binhluan);
        AnhXa();
        getProfile();
        getContentPost();
        getDataComment();
        EventClick();
    }

    private void getProfile() {
        Picasso.with(this).load(String.valueOf(Trangchu.mUser.getPhotoUrl()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_avt);
    }

    private void getDataComment() {
        progressBar_comment.setVisibility(View.VISIBLE);
        DataClient dataClient = APIUntils.getData();
        Call<List<Binhluan>> callback = dataClient.nguoidung_getbinhluan(id_baiviet);
        callback.enqueue(new Callback<List<Binhluan>>() {
            @Override
            public void onResponse(Call<List<Binhluan>> call, Response<List<Binhluan>> response) {
                progressBar_comment.setVisibility(View.GONE);
                mlist = new ArrayList<>();
                List<Binhluan>binhluan = response.body();
                Log.d("messcomment", binhluan.get(0).getId_binhluan());
                    for (int i = 0; i < binhluan.size(); i++) {
                        mlist.add(new Binhluan(binhluan.get(i).getNoidung(), binhluan.get(i).getThoigian(), binhluan.get(i).getTennguoidung(),
                                binhluan.get(i).getAnhdaidien(), binhluan.get(i).getEmail(), binhluan.get(i).getId_binhluan()));
                    }
                adapter = new AdapterBinhluan(ChitietBaiviet.this, R.layout.item_binhluan,mlist);
                lv_dscmt.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                CustomListView(lv_dscmt);

            }

            @Override
            public void onFailure(Call<List<Binhluan>> call, Throwable t) {
                try{
                    Log.d("getcomment", t.getMessage());
                    Log.d("getcommentcall",call.request().body().toString());
                    progressBar_comment.setVisibility(View.GONE);
                    mlist = new ArrayList<>();
                    adapter = new AdapterBinhluan(ChitietBaiviet.this, R.layout.item_binhluan,mlist);
                    adapter.notifyDataSetChanged();
                    lv_dscmt.setAdapter(adapter);
                    CustomListView(lv_dscmt);
                }catch (NullPointerException nes){

                }
//                adapter.notifyDataSetChanged();

            }
        });
    }

    private void getContentPost() {
        Baiviet baiviet = (Baiviet) getIntent().getSerializableExtra("thongtinbaiviet");
        id_baiviet = baiviet.getIdbaiviet();
        email = baiviet.getEmailnguoidung();
        hinhanhbaiviet = baiviet.getHinhanh();
        tv_username.setText(baiviet.getTennguoidung());
        tv_thoigian.setText(baiviet.getThoigian());
        if (TextUtils.isEmpty(baiviet.getTieude())) {
            tv_noidung.setText(baiviet.getNoidung());
        } else {
            tv_noidung.setText(baiviet.getTieude().toUpperCase() + "\n" + baiviet.getNoidung());
        }
        if (TextUtils.isEmpty(baiviet.getHinhanh())) {
            img_hinhanh.setVisibility(View.GONE);
        } else {
            Picasso.with(ChitietBaiviet.this).load(baiviet.getHinhanh())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(img_hinhanh);
        }
        Picasso.with(ChitietBaiviet.this).load(baiviet.getAnhdaidien())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_avt_user);

    }

    private void EventClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
        edt_nhapcmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edt_nhapcmt.getText().toString().length() > 0) {
                    img_send.setVisibility(View.VISIBLE);
                } else {
                    img_send.setVisibility(View.GONE);

                }
            }
        });
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edt_nhapcmt.getText().toString();
                String user_email=Trangchu.mUser.getEmail();
                postComment(noidung, id_baiviet, user_email);
            }
        });

    }

    private void postComment(String noidung, String id_baiviet, String email) {
        DataClient dataClient = APIUntils.getData();
        Call<String> callback = dataClient.nguoidung_thembinhluan(noidung, id_baiviet, email);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if (mess.equals("success")) {
                    getDataComment();
                    edt_nhapcmt.setText("");
                }
                if (mess.equals("fail")) {
                    Toast.makeText(ChitietBaiviet.this, "Khong them dc comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("failsendcmt", t.getMessage());
            }
        });
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(this, img_more);
        popupMenu.getMenuInflater().inflate(R.menu.menu_tuychonbaiviet2, popupMenu.getMenu());
        if (!Trangchu.mUser.getEmail().equals(email)) {
            popupMenu.getMenu().findItem(R.id.menu_tuychon_xoabaiviet).setEnabled(false);
            popupMenu.getMenu().findItem(R.id.menu_tuychon_chinhsua).setEnabled(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_tuychon_chinhsua:
//                        Toast.makeText(getApplicationContext(), "Sửa", Toast.LENGTH_SHORT).show();
                        String ngaythang=tv_thoigian.getText().toString();
                        String noidung=tv_noidung.getText().toString();
                        Intent intent= new Intent(ChitietBaiviet.this,Chinhsuabaiviet.class);
                        intent.putExtra("id_baiviet",id_baiviet);
                        intent.putExtra("ngaythang",ngaythang);
                        intent.putExtra("noidung",noidung);
                        intent.putExtra("hinhanh",hinhanhbaiviet);
                        startActivity(intent);
                        break;
                    case R.id.menu_tuychon_xoabaiviet:
                        final String src_hinhanh;
                        if(TextUtils.isEmpty(hinhanhbaiviet)){
                            src_hinhanh="";
                        }else{
                            src_hinhanh = hinhanhbaiviet.substring(hinhanhbaiviet.lastIndexOf("/"));

                        }
                        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(ChitietBaiviet.this);
                        diaBuilder.setTitle("Xóa bài viết");
                        diaBuilder.setMessage("Bài viết này sẽ vĩnh viễn xóa khỏi trang cá nhân của bạn");
                        diaBuilder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        diaBuilder.setNegativeButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Xoabaiviet(id_baiviet,src_hinhanh);
                            }
                        });
                        diaBuilder.setCancelable(true);
                        AlertDialog showDialog = diaBuilder.create();
                        showDialog.show();

                        break;
                    case R.id.menu_tuychon_trangcanhan:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void Xoabaiviet(final String id_baiviet, final String src_hinhanh) {
        DataClient dataClient = APIUntils.getData();
        Call<String> callback = dataClient.nguoidung_xoabaiviet(id_baiviet, src_hinhanh);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if (mess.equals("success")) {
                    startActivity(new Intent(ChitietBaiviet.this,Trangchu.class));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("failxoabv", t.getMessage());
            }
        });
    }

    private void AnhXa() {
        lv_dscmt = findViewById(R.id.lv_binhluan_comment);
        img_back = findViewById(R.id._img_binhluan_back);
        img_avt = findViewById(R.id.img_binhluan_avt);
        img_avt_user = findViewById(R.id.img_binhluan_avtbaiviet);
        img_hinhanh = findViewById(R.id.img_binhluan_hinhanhbaiviet);
        img_send = findViewById(R.id.img_binhluan_send);
        tv_username = findViewById(R.id.tv_binhluan_username);
        tv_thoigian = findViewById(R.id.tv_binhluan_ngaythang);
        tv_noidung = findViewById(R.id.tv_binhluan_noidungbv);
        edt_nhapcmt = findViewById(R.id.edt_binhluan_noidung);
        img_more = findViewById(R.id.imgbtn_binhluan_more);
        progressBar_comment = findViewById(R.id.progress_comment);
        progressBar_comment.setVisibility(View.VISIBLE);
        img_send.setVisibility(View.GONE);
        registerForContextMenu(lv_dscmt);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_comment, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.menu_comment_xoa) {
            String emailnguoibinhluan = mlist.get(info.position).getEmail();
            if (emailnguoibinhluan.equals(Trangchu.mUser.getEmail())) {
                AlertDialog.Builder diaBuilder = new AlertDialog.Builder(ChitietBaiviet.this);
                diaBuilder.setTitle("Xóa bình luận");
                diaBuilder.setMessage("Bạn có muốn xóa bình luận này không?");
                diaBuilder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                diaBuilder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteComment(Integer.parseInt(mlist.get(info.position).getId_binhluan()));
                    }
                });
                diaBuilder.setCancelable(true);
                AlertDialog showDialog = diaBuilder.create();
                showDialog.show();

            } else {
                AlertDialog.Builder diaBuilder = new AlertDialog.Builder(ChitietBaiviet.this);
                diaBuilder.setTitle("Xóa bình luận");
                diaBuilder.setMessage("Bạn không thể xóa bình luận này");
                diaBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                diaBuilder.setCancelable(true);
                AlertDialog showDialog = diaBuilder.create();
                showDialog.show();
            }
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteComment(int i) {
        DataClient dataClient = APIUntils.getData();
        Call<String> callback = dataClient.nguoidung_xoabinhluan(i);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                Log.d("xoacomment", mess);
                if (mess.equals("success")) {
                    getDataComment();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("failxoacomment", t.getMessage());
            }
        });
    }

    private void CustomListView(ListView lv_dscmt) {
        ListAdapter listAdapter = lv_dscmt.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = lv_dscmt.getPaddingTop() + lv_dscmt.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, lv_dscmt);
            float px = 300 * (lv_dscmt.getResources().getDisplayMetrics().density);
            listItem.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv_dscmt.getLayoutParams();
        params.height = totalHeight + (lv_dscmt.getDividerHeight() * (listAdapter.getCount()-1));
        lv_dscmt.setLayoutParams(params);
        lv_dscmt.requestLayout();
    }


}
