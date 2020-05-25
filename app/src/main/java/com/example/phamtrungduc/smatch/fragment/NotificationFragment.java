package com.example.phamtrungduc.smatch.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.servicenotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.activity.HomeActivity;
import com.example.phamtrungduc.smatch.activity.PostDetails;
import com.example.phamtrungduc.smatch.adapter.NotificationAdapter;
import com.example.phamtrungduc.smatch.entity.Post;
import com.example.phamtrungduc.smatch.retrofit2.APIUntils;
import com.example.phamtrungduc.smatch.retrofit2.DataClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {
    private ListView lv_dsthongbao;
    private NotificationAdapter notificationAdapter;
    private DatabaseReference mDataref;
    private List<Post> mlistthongbao = new ArrayList<>();

    public NotificationFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);
        AnhXa(view);

        Query query=mDataref.child(MyFirebaseMessagingService.mUser.getUid()).orderByKey().limitToLast(8);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()
                        ) {
                    Post p=item.getValue(Post.class);
                    mlistthongbao.add(p);
                }
                Collections.reverse(mlistthongbao);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        lv_dsthongbao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                DataClient dataClient= APIUntils.getData();
                final Call<String> callback= dataClient.kiemtraid(mlistthongbao.get(position).getIdbaiviet());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String mess=response.body();
                                if (mess.equals("success")){
                                    lv_dsthongbao.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent=new Intent(getContext(),PostDetails.class);
                                            intent.putExtra("id_baiviet",mlistthongbao.get(position).getIdbaiviet());
                                            intent.putExtra("email",mlistthongbao.get(position).getEmailnguoidung());
                                            intent.putExtra("hinhanh",mlistthongbao.get(position).getHinhanh());
                                            intent.putExtra("tennguoidung",mlistthongbao.get(position).getTennguoidung());
                                            intent.putExtra("thoigian",mlistthongbao.get(position).getThoigian());
                                            intent.putExtra("noidung",mlistthongbao.get(position).getNoidung());
                                            intent.putExtra("tieude",mlistthongbao.get(position).getTieude());
                                            intent.putExtra("anhdaidien",mlistthongbao.get(position).getAnhdaidien());
                                            startActivity(intent);
                                        }
                                    });

                                }else{
                                    Toast.makeText(getContext(), "Bài viết không còn tồn tại", Toast.LENGTH_SHORT).show();

                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });

                    }
                }).start();

            }
        });
    }

    private void AnhXa(View view) {
        mDataref = FirebaseDatabase.getInstance().getReference();
        lv_dsthongbao = view.findViewById(R.id.lv_dsthongbao);
        notificationAdapter = new NotificationAdapter(getContext(), R.layout.item_fragment_thongbao, mlistthongbao);
        lv_dsthongbao.setAdapter(notificationAdapter);
        registerForContextMenu(lv_dsthongbao);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE,R.id.menu_notifi_offnotifi,Menu.NONE,"Tắt thông báo bài viết");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId()==R.id.menu_notifi_offnotifi){
            final String idbaiviet=mlistthongbao.get(info.position).getIdbaiviet();
            Toast.makeText(getContext(), idbaiviet, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder diaBuilder = new AlertDialog.Builder(getContext());
            diaBuilder.setTitle("Tắt thông báo");
            diaBuilder.setMessage("Bạn sẽ không nhận được thông báo từ bài viết này trong tương lai?");
            diaBuilder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            diaBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(idbaiviet);
                    startActivity(new Intent(getContext(),HomeActivity.class));
                }
            });
            diaBuilder.setCancelable(true);
            AlertDialog showDialog = diaBuilder.create();
            showDialog.show();

            return true;
        }
        return super.onContextItemSelected(item);
    }
}
