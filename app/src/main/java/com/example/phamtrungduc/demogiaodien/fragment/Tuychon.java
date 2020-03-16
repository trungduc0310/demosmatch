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
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tuychon extends Fragment {
    LinearLayout linearLayout;
    TextView tv_logout,tv_changepass;

    FirebaseAuth mAuth;
    public Tuychon() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tuychon,container,false);
        Anhxa(view);
        EventClick();
        return view;
    }

    private void EventClick() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        mAuth.signOut();
        startActivity(new Intent(getContext(),Dangnhap.class));
    }

    private void ChangePass() {
        startActivity(new Intent(getContext(),DoiPass.class));
    }

    private void Anhxa(View view) {
        linearLayout=view.findViewById(R.id.ln_fragmenttuychon_viewprifile);
        tv_logout=view.findViewById(R.id.tv_tuychon_logout);
        tv_changepass=view.findViewById(R.id.tv_tuychon_changepass);
        mAuth=FirebaseAuth.getInstance();
    }
}
