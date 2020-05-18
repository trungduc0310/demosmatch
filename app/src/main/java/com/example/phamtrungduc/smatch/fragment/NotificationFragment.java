package com.example.phamtrungduc.smatch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.activity.HomeActivity;
import com.example.phamtrungduc.smatch.adapter.NotificationAdapter;
import com.example.phamtrungduc.smatch.entity.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class NotificationFragment extends Fragment {
    ListView lv_dsthongbao;
    public static NotificationAdapter notificationAdapter;
    public static List<Post> mlistthongbao= new ArrayList<>();
    public NotificationFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thongbao,container,false);
        AnhXa(view);
        return view;
    }



    private void AnhXa(View view) {
        lv_dsthongbao=view.findViewById(R.id.lv_dsthongbao);
        notificationAdapter= new NotificationAdapter(getContext(),R.layout.item_fragment_thongbao,mlistthongbao);
        lv_dsthongbao.setAdapter(notificationAdapter);

    }


}
