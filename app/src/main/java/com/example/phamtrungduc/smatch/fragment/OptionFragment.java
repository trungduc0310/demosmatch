package com.example.phamtrungduc.smatch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.servicenotification.MyFirebaseMessagingService;
import com.example.phamtrungduc.smatch.activity.LoginActivity;
import com.example.phamtrungduc.smatch.activity.ChangepasswordActivity;
import com.example.phamtrungduc.smatch.activity.PageUserActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class OptionFragment extends Fragment {
    private LinearLayout linearLayout;
    private TextView tv_logout,tv_changepass,tv_username;
    private CircleImageView img_avt;


    public OptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tuychon,container,false);
        Anhxa(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getProfile();
        EventClick();
    }

    private void getProfile() {
        Picasso.with(getContext()).load(String.valueOf(MyFirebaseMessagingService.mUser.getPhotoUrl()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img_avt);
        tv_username.setText(MyFirebaseMessagingService.mUser.getDisplayName());
    }

    private void EventClick() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PageUserActivity.class));
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
        FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
        MyFirebaseMessagingService.mAuth.signOut();
        startActivity(new Intent(getContext(),LoginActivity.class));
    }

    private void ChangePass() {
        startActivity(new Intent(getContext(),ChangepasswordActivity.class));
    }

    private void Anhxa(View view) {
        linearLayout=view.findViewById(R.id.ln_fragmenttuychon_viewprifile);
        tv_logout=view.findViewById(R.id.tv_tuychon_logout);
        tv_changepass=view.findViewById(R.id.tv_tuychon_changepass);
        img_avt=view.findViewById(R.id.img_tuychon_avt);
        tv_username=view.findViewById(R.id.tv_tuychon_name);

    }
}
