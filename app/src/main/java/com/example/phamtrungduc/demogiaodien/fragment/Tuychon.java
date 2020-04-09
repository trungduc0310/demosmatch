package com.example.phamtrungduc.demogiaodien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.activity.Dangnhap;
import com.example.phamtrungduc.demogiaodien.activity.DoiPass;
import com.example.phamtrungduc.demogiaodien.activity.PageUser;
import com.example.phamtrungduc.demogiaodien.activity.Trangchu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Tuychon extends Fragment {
    LinearLayout linearLayout;
    TextView tv_logout,tv_changepass,tv_username;
    CircleImageView img_avt;


    public Tuychon() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tuychon,container,false);
        Anhxa(view);
        getProfile();
        EventClick();
        return view;
    }

    private void getProfile() {
        Picasso.with(getContext()).load(String.valueOf(Trangchu.mUser.getPhotoUrl()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_avt);
        tv_username.setText(Trangchu.mUser.getDisplayName());
    }

    private void EventClick() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PageUser.class));
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        tv_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePass();
            }
        });
    }

    private void Logout() {
        Trangchu.mAuth.signOut();
        startActivity(new Intent(getContext(),Dangnhap.class));
    }

    private void ChangePass() {
        startActivity(new Intent(getContext(),DoiPass.class));
    }

    private void Anhxa(View view) {
        linearLayout=view.findViewById(R.id.ln_fragmenttuychon_viewprifile);
        tv_logout=view.findViewById(R.id.tv_tuychon_logout);
        tv_changepass=view.findViewById(R.id.tv_tuychon_changepass);
        img_avt=view.findViewById(R.id.img_tuychon_avt);
        tv_username=view.findViewById(R.id.tv_tuychon_name);

    }
}
