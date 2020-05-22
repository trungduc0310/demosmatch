package com.example.phamtrungduc.smatch.ServiceNotification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.activity.HomeActivity;
import com.example.phamtrungduc.smatch.activity.PostDetails;
import com.example.phamtrungduc.smatch.entity.Post;
import com.example.phamtrungduc.smatch.fragment.NotificationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;
    public static DatabaseReference mData=FirebaseDatabase.getInstance().getReference();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String,String> map=remoteMessage.getData();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        Log.d("Uidnguoidung_service", mUser.getUid());
        AddNotification(map);
        showNotification(map);
    }

    private void AddNotification(Map<String,String> map) {
        String id_baiviet=map.get("id_baiviet");
        String email=map.get("email");
        String hinhanh=map.get("hinhanh");
        String tennguoidung=map.get("tennguoidung");
        String thoigian=map.get("thoigian");
        String noidung=map.get("noidung");
        String tieude=map.get("tieude");
        String anhdaidien=map.get("anhdaidien");
        Post post=new Post(id_baiviet,email,tennguoidung,noidung,thoigian,tieude,hinhanh,anhdaidien);
        mData.child(mUser.getUid()).push().setValue(post);
    }

    private void showNotification(Map<String,String> map) {
        String title=map.get("title");
        String body=map.get("body");
        String id_baiviet=map.get("id_baiviet");
        String email=map.get("email");
        String hinhanh=map.get("hinhanh");
        String tennguoidung=map.get("tennguoidung");
        String thoigian=map.get("thoigian");
        String noidung=map.get("noidung");
        String tieude=map.get("tieude");
        String anhdaidien=map.get("anhdaidien");
        Intent intent=new Intent(this,PostDetails.class);
        intent.putExtra("id_baiviet",id_baiviet);
        intent.putExtra("email",email);
        intent.putExtra("hinhanh",hinhanh);
        intent.putExtra("tennguoidung",tennguoidung);
        intent.putExtra("thoigian",thoigian);
        intent.putExtra("noidung",noidung);
        intent.putExtra("tieude",tieude);
        intent.putExtra("anhdaidien",anhdaidien);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

//        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("TAG","TAG",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("TAG");
            notificationManager.createNotificationChannel(channel);
        }
        Uri u=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"TAG")
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(u)
                .setContentIntent(pendingIntent);

        notificationManager.notify(0,builder.build());
    }
//    public String xulyngay(String ngay) {
//        String[] xuly = ngay.split("\\-");
//        ngay = xuly[2] + "/" + xuly[1] + "/" + xuly[0];
//        return ngay;
//    }
}
