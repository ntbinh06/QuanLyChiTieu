package com.example.quanlychitieu.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Controller.Ctrl_XemTKChiTiet;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class V_TongQuan_CacTaiKhoan extends RecyclerView.Adapter<V_TongQuan_CacTaiKhoan.ViewHolder> {

    private Context context;
    private List<M_TaiKhoan> taiKhoanList;

    public V_TongQuan_CacTaiKhoan(Context context, List<M_TaiKhoan> taiKhoanList) {
        this.context = context;
        this.taiKhoanList = taiKhoanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_tongquan_cactaikhoan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        M_TaiKhoan taiKhoan = taiKhoanList.get(position);
        holder.bind(taiKhoan);
    }

    @Override
    public int getItemCount() {
        return taiKhoanList.size();
    }

    // ViewHolder class to hold item views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtenTaiKhoan, tvTongSoTien, tvLanSDCuoi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenTaiKhoan = itemView.findViewById(R.id.tenTaiKhoan);
            tvTongSoTien = itemView.findViewById(R.id.tienTaiKhoan);
            tvLanSDCuoi = itemView.findViewById(R.id.lansudungCuoi);
        }

        public void bind(M_TaiKhoan taiKhoan) {
            tvtenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
            tvTongSoTien.setText(String.valueOf(taiKhoan.getLuongBanDau()));
            tvLanSDCuoi.setText(taiKhoan.getFormattedLanSuDungCuoi());
        }
    }
}
