package com.example.quanlychitieu.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;

import java.util.List;

public class V_TongQuan_CacGiaoDich extends RecyclerView.Adapter<V_TongQuan_CacGiaoDich.ViewHolder>{

    private Context context;
    private List<M_GiaoDich> myList;

    // Constructor
    public V_TongQuan_CacGiaoDich(Context context, List<M_GiaoDich> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_tongquan_cacgiaodic, parent, false);
        return new V_TongQuan_CacGiaoDich.ViewHolder(view);
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
            loaiGd = itemView.findViewById(R.id.loaiGd);
        }

        // Sửa phương thức bind() để nhận thêm tham số Context
        public void bind(M_GiaoDich giaoDich, Context context) {
            tenGD.setText(giaoDich.getIdHangMuc());
            taiKhoan.setText(giaoDich.getIdTaiKhoan());
            tien.setText(String.valueOf(giaoDich.getGiaTri()));
            ngay.setText(giaoDich.getFormattedNgayTao());

        }
    }
}
