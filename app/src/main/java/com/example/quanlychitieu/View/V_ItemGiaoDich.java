package com.example.quanlychitieu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;

import java.util.List;

public class V_ItemGiaoDich extends RecyclerView.Adapter<V_ItemGiaoDich.ViewHolder> {

    private Context context;
    private List<M_GiaoDich> myList;

    public V_ItemGiaoDich(Context context, List<M_GiaoDich> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cacdd, parent, false); // Đảm bảo layout đúng
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        M_GiaoDich giaoDich = myList.get(position);
        holder.bind(giaoDich);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenGD, taiKhoan, tien, ngay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenGD = itemView.findViewById(R.id.textDescription);
            taiKhoan = itemView.findViewById(R.id.textAccount);
            tien = itemView.findViewById(R.id.textAmount);
            ngay = itemView.findViewById(R.id.textDate);
        }

        public void bind(M_GiaoDich giaoDich) {
            tenGD.setText(giaoDich.getIdHangMuc());
            taiKhoan.setText(giaoDich.getIdTaiKhoan());
            tien.setText(String.valueOf(giaoDich.getGiaTri()));
            ngay.setText(giaoDich.getFormattedNgayTao());
        }
    }
}