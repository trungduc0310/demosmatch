package com.example.phamtrungduc.demogiaodien.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamtrungduc.demogiaodien.R;
import com.example.phamtrungduc.demogiaodien.activity.ChitietBaiviet;
import com.example.phamtrungduc.demogiaodien.activity.PageUser;
import com.example.phamtrungduc.demogiaodien.activity.Trangchu;
import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        CircleImageView imgavt;
        TextView tvuser, tvdate, tvnoidung;
        Button btn_chitietbaiviet;
        ImageView img_hinhanhbaiviet;
        ImageButton imgbtn_more;

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
            viewHolder.tvuser = convertView.findViewById(R.id.itempagefriend_tv_username);
            viewHolder.btn_chitietbaiviet = convertView.findViewById(R.id.itempagefriend_btn_binhluan);
            viewHolder.img_hinhanhbaiviet = convertView.findViewById(R.id.item_img_itempagefriend_hinhanhbaiviet);
            viewHolder.imgbtn_more = convertView.findViewById(R.id.imgbtn_itembaiviet_more);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvuser.setText(mList.get(position).getTennguoidung());
        if (TextUtils.isEmpty(mList.get(position).getTieude())) {
            viewHolder.tvnoidung.setText(mList.get(position).getNoidung());

        } else {
            viewHolder.tvnoidung.setText(mList.get(position).getTieude().toUpperCase() + "\n" + mList.get(position).getNoidung());
        }
        viewHolder.tvdate.setText(xulyngay(mList.get(position).getThoigian()));

        Picasso.with(mContext).load(mList.get(position).getAnhdaidien())
                .error(R.drawable.ic_broken_image_black_24dp)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(viewHolder.imgavt);
        viewHolder.tvuser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PageUser.class);
                intent.putExtra("thongtinbaiviet", mList.get(position));
                mContext.startActivity(intent);
            }
        });
        viewHolder.btn_chitietbaiviet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChitietBaiviet.class);
                intent.putExtra("thongtinbaiviet", mList.get(position));
                mContext.startActivity(intent);
            }
        });
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.imgbtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(finalViewHolder.imgbtn_more,position,mList);
            }
        });
        if (TextUtils.isEmpty(mList.get(position).getHinhanh())){
            viewHolder.img_hinhanhbaiviet.setImageResource(R.drawable.googleg_standard_color_18);
        }else {
            Picasso.with(mContext).load(mList.get(position).getHinhanh())
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(viewHolder.img_hinhanhbaiviet);
        }
        return convertView;
    }

    private void showMenu(ImageButton btn_more,int position, List<Baiviet> mlist) {
        PopupMenu popupMenu = new PopupMenu(mContext, btn_more);
        popupMenu.getMenuInflater().inflate(R.menu.menu_tuychonbaiviet, popupMenu.getMenu());
        if (!Trangchu.mUser.getEmail().equals(mlist.get(position).getEmailnguoidung())){
            popupMenu.getMenu().findItem(R.id.menu_tuychon_xoabaiviet).setEnabled(false);
            popupMenu.getMenu().findItem(R.id.menu_tuychon_chinhsua).setEnabled(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_tuychon_chinhsua:
                        Toast.makeText(mContext, "Sửa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_tuychon_xoabaiviet:
                        Toast.makeText(mContext, "Xóa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_tuychon_trangcanhan:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public String xulyngay(String ngay) {
        String[] xuly = ngay.split("\\-");
        ngay = xuly[2] + "/" + xuly[1] + "/" + xuly[0];
        return ngay;
    }
}
