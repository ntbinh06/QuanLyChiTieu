package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc; // Sử dụng model mới
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_HangMucThuNhap extends ArrayAdapter<M_DanhMucHangMuc> { // Thay đổi kiểu dữ liệu
    private Context context;
    private int resource;
    private List<M_DanhMucHangMuc> arrContact; // Cập nhật kiểu danh sách

    public Ctrl_HangMucThuNhap(Context context, int resource, ArrayList<M_DanhMucHangMuc> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAvatar = convertView.findViewById(R.id.image_hangMuc);
            viewHolder.tvName = convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy đối tượng M_DanhMucHangMuc từ danh sách
        M_DanhMucHangMuc danhMuc = arrContact.get(position);
        viewHolder.tvName.setText(danhMuc.getTenHangmuc()); // Cập nhật để lấy tên hạng mục

        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView tvAvatar;
    }
}