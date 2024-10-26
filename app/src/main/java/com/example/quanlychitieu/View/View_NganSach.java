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
    private List<M_NganSach> arrNganSach;

    public View_NganSach(Context context, List<M_NganSach> arrNganSach) {
        this.context = context;
        this.arrNganSach = arrNganSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_ngansach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        M_NganSach nganSach = arrNganSach.get(position);
        holder.bind(nganSach);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietNganSach.class);
            intent.putExtra("transactionId", position); // Hoặc sử dụng một ID duy nhất
            intent.putExtra("tenHangMuc", nganSach.getTenHangMuc());
            intent.putExtra("soTien", nganSach.getTongSoTien());
            intent.putExtra("soTienConLai", nganSach.getSoTienConLai());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrNganSach.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

        public void bind(M_NganSach nganSach) {
            imgHangMuc.setImageResource(nganSach.getHinhAnh());
            tenHangMuc.setText(nganSach.getTenHangMuc());
            soTien.setText(nganSach.getFormattedSoTien());
            tienConLai.setText(nganSach.getFormattedSoTienConLai());
            pgbTienTrinh.setProgress(nganSach.getPgrBar());
        }
    }
}
