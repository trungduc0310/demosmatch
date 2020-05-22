package com.example.phamtrungduc.smatch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.phamtrungduc.smatch.R;
import com.example.phamtrungduc.smatch.activity.PageUserActivity;
import com.example.phamtrungduc.smatch.activity.PostDetails;
import com.example.phamtrungduc.smatch.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewFeedAdapter extends ArrayAdapter<Post> {
    private Context mContext;
    private int mLayout;
    private List<Post> mList;

    public NewFeedAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayout = resource;
        mList = objects;
    }

    public class ViewHolder {
        CircleImageView imgavt;
        TextView tvuser, tvdate, tvnoidung, tv_more;
        TextView btn_chitietbaiviet;
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
        viewHolder.tvdate.setText(mList.get(position).getThoigian());

        Picasso.with(mContext).load(mList.get(position).getAnhdaidien())
                .error(R.drawable.ic_broken_image_black_24dp)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(viewHolder.imgavt);
        viewHolder.tvuser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PageUserActivity.class);
                intent.putExtra("thongtinbaiviet", mList.get(position));
                mContext.startActivity(intent);
            }
        });
        viewHolder.btn_chitietbaiviet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostDetails.class);
                intent.putExtra("thongtinbaiviet", mList.get(position));
                mContext.startActivity(intent);
            }
        });
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.imgbtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(finalViewHolder.imgbtn_more, position);
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;

        String hinhanh = mList.get(position).getHinhanh();
        if (TextUtils.isEmpty(hinhanh)) {
            finalViewHolder1.img_hinhanhbaiviet.setPadding(30, 30, 30, 30);
            finalViewHolder1.img_hinhanhbaiviet.setImageResource(R.drawable.footballicon);
        } else {
            Picasso.with(mContext).load(hinhanh)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(finalViewHolder1.img_hinhanhbaiviet);
        }

        viewHolder.img_hinhanhbaiviet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowdialogIMG(mList.get(position).getHinhanh());
            }
        });
        return convertView;
    }

    private void ShowdialogIMG(String hinhanh) {
        try {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_xemhinhanh, null);
            ImageView imgview_dialog = view.findViewById(R.id.img_dialogxemanh_img);
            Picasso.with(getContext()).load(hinhanh)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(imgview_dialog);
            AlertDialog.Builder diaBuilder = new AlertDialog.Builder(getContext());
            diaBuilder.setView(view);
            diaBuilder.setCancelable(true);
            AlertDialog showDialog = diaBuilder.create();
            showDialog.show();
        } catch (IllegalArgumentException nullex) {

        }

    }

    private void showMenu(ImageButton btn_more, final int position) {
        PopupMenu popupMenu = new PopupMenu(mContext, btn_more);
        popupMenu.getMenuInflater().inflate(R.menu.menu_tuychonbaiviet1, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_tuychon_trangcanhan:
                        Intent intent = new Intent(mContext, PageUserActivity.class);
                        intent.putExtra("thongtinbaiviet", mList.get(position));
                        mContext.startActivity(intent);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }


}
