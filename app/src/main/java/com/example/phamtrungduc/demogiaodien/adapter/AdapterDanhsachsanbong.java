package com.example.phamtrungduc.demogiaodien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.entity.Sanbong;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDanhsachsanbong extends ArrayAdapter<Sanbong> {
    Context mContext;
    int mLayout;
    List<Sanbong> mList;
    public AdapterDanhsachsanbong( Context context, int resource,List<Sanbong> objects) {
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
        viewHolder.imgcall=convertView.findViewById(R.id.ibtn_item_dssanbong_lienlac);
        viewHolder.tvsdt.setText("Số điện thoại: "+mList.get(position).getSdt());
        viewHolder.tvtensan.setText(mList.get(position).getTensan());
        viewHolder.tvdiachi.setText("Địa chỉ: "+mList.get(position).getDiachi());
        Picasso.with(mContext).load(mList.get(position).getHinhanh()).
                error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(viewHolder.imgavt);
        viewHolder.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Số điện thoại: "+mList.get(position).getSdt(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
