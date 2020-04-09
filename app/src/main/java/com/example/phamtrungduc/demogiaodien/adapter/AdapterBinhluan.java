package com.example.phamtrungduc.demogiaodien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.entity.Binhluan;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhluan extends ArrayAdapter<Binhluan> {
    Context mcontext;
    int mlayout;
    List<Binhluan> mlist;
    public AdapterBinhluan(@NonNull Context context, int resource, @NonNull List<Binhluan> objects) {
        super(context, resource, objects);
        mcontext=context;
        mlayout=resource;
        mlist=objects;
    }
    public class ViewHolder{
        TextView tv_noidung,tv_ngaythang,tv_username;
        CircleImageView img_avt;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mlayout,null);
            viewHolder.img_avt=convertView.findViewById(R.id.img_itembinhluan_avt);
            viewHolder.tv_ngaythang=convertView.findViewById(R.id.tv_itembinhluan_ngaythang);
            viewHolder.tv_noidung=convertView.findViewById(R.id.tv_itembinhluan_noidung);
            viewHolder.tv_username=convertView.findViewById(R.id.tv_itembinhluan_username);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_noidung.setText(mlist.get(position).getNoidung());
        viewHolder.tv_ngaythang.setText(mlist.get(position).getThoigian());
        viewHolder.tv_username.setText(mlist.get(position).getTennguoidung());
        Picasso.with(mcontext).load(mlist.get(position).getAnhdaidien())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(android.R.drawable.ic_menu_report_image)
                .into(viewHolder.img_avt);
        return convertView;
    }
}
