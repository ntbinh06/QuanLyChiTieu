package com.example.quanlychitieu.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Controller.ChiTietNganSach;
import com.example.quanlychitieu.Model.M_NganSach;
import com.example.quanlychitieu.R;

import java.util.List;

public class View_NganSach extends RecyclerView.Adapter<View_NganSach.ViewHolder> {

    private Context context;
    private List<M_NganSach> arrContact;

    public View_NganSach(Context context, List<M_NganSach> arrContact) {
        this.context = context;
        this.arrContact = arrContact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout (listview_ngansach.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.listview_ngansach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views
        M_NganSach contact = arrContact.get(position);
        holder.imgHangMuc.setImageResource(contact.getImgHangMuc());
        holder.tenHangMuc.setText(contact.getTenHangMuc());
        holder.soTien.setText(contact.getFormattedSoTien());
        holder.tienConLai.setText(contact.getFormattedSoTienConLai());
        holder.conlai.setText(contact.getTxtConlai());
        holder.pgbTienTrinh.setProgress(contact.getPgrBar());
        //
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietNganSach.class);
            // Truyền dữ liệu qua Intent
            intent.putExtra("imgCategory", contact.getImgHangMuc());
            intent.putExtra("tenHangMuc", contact.getTenHangMuc());
            intent.putExtra("soTien", contact.getSoTien());
            intent.putExtra("soTienConLai", contact.getSoTienConLai());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrContact.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define your views
        TextView tenHangMuc, soTien, conlai, tienConLai;
        ImageView imgHangMuc;
        ProgressBar pgbTienTrinh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenHangMuc = itemView.findViewById(R.id.tenHangMuc);
            soTien = itemView.findViewById(R.id.soTien);
            conlai = itemView.findViewById(R.id.conlai);
            tienConLai = itemView.findViewById(R.id.tienConLai);
            imgHangMuc = itemView.findViewById(R.id.imgHangMuc);
            pgbTienTrinh = itemView.findViewById(R.id.pgbTienTrinh);
        }
    }
}
