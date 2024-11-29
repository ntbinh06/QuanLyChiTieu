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
            loaiGd = itemView.findViewById(R.id.loaiGd);
        }

        // Sửa phương thức bind() để nhận thêm tham số Context
        public void bind(M_GiaoDich giaoDich, Context context) {
            tenGD.setText(giaoDich.getIdHangMuc());
            taiKhoan.setText(giaoDich.getIdTaiKhoan());
            tien.setText(String.valueOf(giaoDich.getGiaTri()));
            ngay.setText(giaoDich.getFormattedNgayTao());

            // Lấy idNhomHangMuc từ Firebase qua idHangMuc
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
            dbRef.child("HangMuc").child(giaoDich.getIdHangMuc()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String idNhomHangMuc = snapshot.child("idNhom").getValue(String.class);

                    // Cập nhật màu cho thanh theo idNhomHangMuc
                    if ("1".equals(idNhomHangMuc)) { // Nhóm "Thu nhập"
                        loaiGd.setColorFilter(ContextCompat.getColor(context, R.color.green));
                    } else if ("2".equals(idNhomHangMuc)) { // Nhóm "Chi phí"
                        loaiGd.setColorFilter(ContextCompat.getColor(context, R.color.red_2));
                    } else {
                        loaiGd.setColorFilter(ContextCompat.getColor(context, R.color.gray_9A));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error fetching HangMuc", error.toException());
                }
            });
        }
    }
}
