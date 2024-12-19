package com.example.quanlychitieu.View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Controller.Ctrl_XemChiPhi;
import com.example.quanlychitieu.Controller.Ctrl_XemThuNhap;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_NhomHangMuc;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class V_ItemGiaoDich extends RecyclerView.Adapter<V_ItemGiaoDich.ViewHolder> {

    private Context context;
    private List<M_GiaoDich> myList;

    // Constructor
    public V_ItemGiaoDich(Context context, List<M_GiaoDich> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cacdd, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        M_GiaoDich giaoDich = myList.get(position);
        holder.bind(giaoDich, context);  // Truyền context vào đây
    }

    public M_GiaoDich getItem(int position) {
        return myList.get(position);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenGD, taiKhoan, tien, ngay;
        ImageView loaiGd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenGD = itemView.findViewById(R.id.textDescription);
            taiKhoan = itemView.findViewById(R.id.textAccount);
            tien = itemView.findViewById(R.id.textAmount);
            ngay = itemView.findViewById(R.id.textDate);
            loaiGd = itemView.findViewById(R.id.img_DM);
        }

        // Sửa phương thức bind() để nhận thêm tham số Context
        public void bind(M_GiaoDich giaoDich, Context context) {
            tenGD.setText(giaoDich.getTenHangMuc());
            taiKhoan.setText(giaoDich.getTenTaiKhoan());
            tien.setText(String.valueOf(giaoDich.getGiaTri()));
            ngay.setText(giaoDich.getFormattedNgayTao());

            // Hiển thị ảnh từ drawable
            String anhHangMuc = giaoDich.getAnhHangMuc();
            if (anhHangMuc != null && !anhHangMuc.isEmpty()) {
                int drawableId = context.getResources().getIdentifier(anhHangMuc, "drawable", context.getPackageName());
                if (drawableId != 0) {
                    loaiGd.setImageResource(drawableId); // Gán ảnh vào img_DM
                } else {
                    loaiGd.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu không tìm thấy
                }
            } else {
                loaiGd.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu không có tên ảnh
            }
        }
    }

    public void updateData(List<M_GiaoDich> newData) {
        this.myList.clear(); // Làm rỗng danh sách trước
        if (newData != null) {
            this.myList.addAll(newData); // Thêm dữ liệu mới
        }
        notifyDataSetChanged(); // Làm mới RecyclerView
    }
}