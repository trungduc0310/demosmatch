package com.example.phamtrungduc.demogiaodien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.entity.Khuvuc;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdapterKhuvuc extends ArrayAdapter<Khuvuc> {
    Context mcontext;
    int mlayout;
    List<Khuvuc> mlist;
    public AdapterKhuvuc(@NonNull Context context, int resource, @NonNull List<Khuvuc> objects) {
        super(context, resource, objects);
        mcontext=context;
        mlayout=resource;
        mlist=objects;
    }
    public class ViewHolder{
        TextView tv_tenkhuvuc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mlayout,null);
            viewHolder.tv_tenkhuvuc=convertView.findViewById(R.id.tv_itemkhuvuc_tenkhuvuc);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_tenkhuvuc.setText(mlist.get(position).getTenkhuvuc());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mlayout,null);
            viewHolder.tv_tenkhuvuc=convertView.findViewById(R.id.tv_itemkhuvuc_tenkhuvuc);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_tenkhuvuc.setText(mlist.get(position).getTenkhuvuc());
        return convertView;
    }
}
