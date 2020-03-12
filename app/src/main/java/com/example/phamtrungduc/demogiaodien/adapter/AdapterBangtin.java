package com.example.phamtrungduc.demogiaodien.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.activity.PageUser;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class AdapterBangtin extends ArrayAdapter<Baiviet> {
    Context mContext;
    int mLayout;
    List<Baiviet> mList;

    public AdapterBangtin(Context context, int resource, List<Baiviet> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayout = resource;
        mList = objects;
    }

    public class ViewHolder {
        ImageView imgavt;
        TextView tvuser, tvdate, tvtieude, tvnoidung;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, null);
            viewHolder.imgavt = convertView.findViewById(R.id.itempagefriend_avt);
            viewHolder.tvdate = convertView.findViewById(R.id.itempagefriend_tv_ngaythang);
            viewHolder.tvnoidung = convertView.findViewById(R.id.itempagefriend_tv_noidungbv);
            viewHolder.tvtieude = convertView.findViewById(R.id.itempagefriend_tv_tieudebv);
            viewHolder.tvuser = convertView.findViewById(R.id.itempagefriend_tv_username);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvuser.setText(mList.get(position).getUsername());
        viewHolder.tvtieude.setText(mList.get(position).getTieude());
        viewHolder.tvnoidung.setText(mList.get(position).getNoidung());
        viewHolder.tvdate.setText(mList.get(position).getDatetime());
        Picasso.with(mContext).load(mList.get(position).getAvt())
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_foreground).into(viewHolder.imgavt);
        viewHolder.tvuser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PageUser.class);
                intent.putExtra("thongtinnguoidung", mList.get(position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}
