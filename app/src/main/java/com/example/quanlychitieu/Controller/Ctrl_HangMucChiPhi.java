package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class Ctrl_HangMucChiPhi extends ArrayAdapter<M_DanhMucHangMuc> {
    private final Context context;
    private final int resource;
    private final ArrayList<M_DanhMucHangMuc> arrContact;

    public Ctrl_HangMucChiPhi(Context context, int resource, ArrayList<M_DanhMucHangMuc> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }


            // Ánh xạ các view trong ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.tvAvatar = convertView.findViewById(R.id.image_hangMuc);
            viewHolder.tvName = convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu cho vị trí hiện tại
        M_DanhMucHangMuc danhMuc = arrContact.get(position);
        viewHolder.tvName.setText(danhMuc.getTenHangmuc());

        // Hiển thị hình ảnh từ drawable
        String anhHangMuc = danhMuc.getAnhHangmuc(); // Lấy tên ảnh từ thuộc tính anhHangMuc
        if (anhHangMuc != null && !anhHangMuc.isEmpty()) {
            int drawableId = context.getResources().getIdentifier(anhHangMuc, "drawable", context.getPackageName());
            Log.d("Debug1", "Tên ảnh: " + danhMuc.getAnhHangmuc());
            if (drawableId != 0) {
                viewHolder.tvAvatar.setImageResource(drawableId); // Gán ảnh vào ImageView
            } else {
                viewHolder.tvAvatar.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu không tìm thấy
            }
        } else {
            viewHolder.tvAvatar.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu anhHangMuc null
        }


        return convertView;
    }

    // Phương thức xử lý hành động khi click vào ic_lock


    // ViewHolder để giữ các view
    private static class ViewHolder {
        ImageView tvAvatar;
        TextView tvName;
    }
}
