package com.example.phamtrungduc.smatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.entity.Match;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchAdapter extends ArrayAdapter<Match> {
    Context mContext;
    int mLayout;
    List<Match> mList;
    String icon_hinhanh="http://icons.iconarchive.com/icons/iconshock/soccer/256/soccer-4-icon.png";
    public MatchAdapter(Context context, int resource, List<Match> objects) {
        super(context, resource, objects);
        mContext=context;
        mLayout=resource;
        mList=objects;
    }
    public class ViewHolder{
        TextView tvtensan,tvdiachi,tvsdt;
        ImageView imgavt;
       ImageButton imgcall;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mLayout,null);
            viewHolder.tvsdt=convertView.findViewById(R.id.tv_item_dssanbong_sodienthoai);
            viewHolder.tvdiachi=convertView.findViewById(R.id.tv_item_dssanbong_diachi);
            viewHolder.tvtensan=convertView.findViewById(R.id.tv_item_dssanbong_tensan);
            viewHolder.imgavt=convertView.findViewById(R.id.img_item_dssanbong_hinhanh);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tvsdt.setText("Số điện thoại: "+mList.get(position).getSodienthoai());
        viewHolder.tvtensan.setText(mList.get(position).getTensanbong());
        viewHolder.tvdiachi.setText("Địa chỉ: "+mList.get(position).getDiachi());
        Picasso.with(mContext).load(icon_hinhanh).
                error(R.drawable.ic_broken_image_black_24dp)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(viewHolder.imgavt);
        return convertView;
    }
}
