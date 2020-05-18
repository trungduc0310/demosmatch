package com.example.phamtrungduc.smatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.entity.Post;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotificationAdapter extends ArrayAdapter<Post> {
    Context mcontext;
    int mlayout;
    List<Post> mlist;
    public NotificationAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mcontext=context;
        mlayout=resource;
        mlist=objects;
    }
    public class ViewHolder{
        TextView tv_title,tv_content;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mlayout,null);
            viewHolder.tv_content=convertView.findViewById(R.id.tv_itemthongbao_content);
            viewHolder.tv_title=convertView.findViewById(R.id.tv_itemthongbao_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(mlist.get(position).getTieude());
        viewHolder.tv_content.setText(mlist.get(position).getNoidung());
        return convertView;
    }
}
