package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.util.Log;
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
        Log.d("Debug1", "Đã vào hàm getView"); // Kiểm tra xem hàm có được gọi không
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

        // Kiểm tra giá trị của danhMuc
        Log.d("Debug1", "Tên danh mục: " + danhMuc.getTenHangmuc());
        Log.d("Debug1", "Tên ảnh: " + danhMuc.getAnhHangmuc());

        viewHolder.tvName.setText(danhMuc.getTenHangmuc()); // Cập nhật để lấy tên hạng mục

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

    public class ViewHolder {
        TextView tvName;
        ImageView tvAvatar;
    }
}